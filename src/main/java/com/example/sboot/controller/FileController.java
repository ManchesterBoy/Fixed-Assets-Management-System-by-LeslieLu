package com.example.sboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Constants;
import com.example.sboot.common.RedisClearCache;
import com.example.sboot.common.Result;
import com.example.sboot.entity.Files;
import com.example.sboot.entity.User;
import com.example.sboot.mapper.FileMapper;
import com.example.sboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关接口
 * @author LeslieLu
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.paths}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisClearCache redisClearCache;


    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */

    @PostMapping("upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUid = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUid);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        String md5;
        String url;
        //上传文件到磁盘
        file.transferTo(uploadFile);
        //获取文件的md5
        md5 = SecureUtil.md5(uploadFile);
        //从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if(dbFiles != null){
            url = dbFiles.getUrl();
            //由于文件已存在，所以删除刚才上传的重复的文件
            uploadFile.delete();
        }else{
            //数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:9090/file/" + fileUUid;
        }

//        //当文件存在的时候，在获取文件的md5
//        if (uploadFile.exists()) {
//            //获取文件的md5，通过对比md5避免重复上传相同内容的文件
//            md5 = SecureUtil.md5(uploadFile);
//            //从数据库查询是否存在相同的记录
//            Files dbFiles = getFileByMd5(md5);
//            //获取文件url
//            if(dbFiles != null){
//                url = dbFiles.getUrl();
//            }else{
//                //把获取到的文件存储到磁盘目录
//                file.transferTo(uploadFile);
//                url = "http://localhost:9090/file/" + fileUUid;
//            }
//        }else{
//
//        }


        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

//        redisClearCache.flushRedis(Constants.FILES_KEY);

        return url;

    }

    /**
     * 文件下载接口  http//:localhost:9090/file/{fileUUid}
     * @param fileUUid
     * @param response
     * @throws IOException
     */

    @GetMapping("/{fileUUid}")
    public void download(@PathVariable String fileUUid, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUid);
        //设置出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUid,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */

    private Files getFileByMd5(String md5){
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);

    }


    //删除
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
//        redisClearCache.flushRedis(Constants.FILES_KEY);
        return Result.success();
    }


    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        //select *from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }

//        redisClearCache.flushRedis(Constants.FILES_KEY);
        return Result.success();
    }


    //更新
    @PostMapping("/update")
    public Result save(@RequestBody Files files){
        fileMapper.updateById(files);
//        redisClearCache.flushRedis(Constants.FILES_KEY);
        return Result.success();
    }



    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();

        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");     //页面数据倒序显示

        //单条件模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }

        /**
         * redis减轻数据库压力
         */
        //1.从缓存获取数据
//        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        Page<Files> filesPage;
//        if(StrUtil.isBlank(jsonStr)){   //2.取出来的json是空的
//            filesPage = fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);     //3.从数据库取出数据
//            //4.在去缓存到redis
//            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY,JSONUtil.toJsonPrettyStr(filesPage));
//        }else{
//            //5.如果有，从redis缓存中获取数据
//            filesPage = JSONUtil.toBean(jsonStr, new TypeReference<Page<Files>>() {
//            }, true);
//        }
//        return Result.success(filesPage);
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }



}

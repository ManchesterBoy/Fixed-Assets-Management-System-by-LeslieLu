package com.example.sboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Constants;
import com.example.sboot.common.Result;
import com.example.sboot.controller.dto.UserDTO;
import com.example.sboot.controller.dto.UserPasswordDTO;
import com.example.sboot.entity.Assets;
import com.example.sboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.sboot.service.IUserService;
import com.example.sboot.entity.User;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  用户管理控制器
 * </p>
 *
 * @author LeslieLu
 * @since 2023-01-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;


    //前端登录接口
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    //前端注册接口
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String role = userDTO.getRole();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)  || StrUtil.isBlank(role)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    //个人信息获取名字
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }


    //获得当前登录用户的昵称
    @GetMapping("/assets/{nickname}")
    public Result findUser(@PathVariable String nickname) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname",nickname);
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }


    //已开启逻辑删除
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    //已开启逻辑删除
    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }

    //查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    //根据角色去查询相关用户
    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role);
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    //根据ID查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");     //页面数据倒序显示

        //单条件与多条件的模糊查询
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }

//        //获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("获取当前用户信息=====================" + currentUser.getNickname());

        return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper));
//        return Result.success(userService.findPage(new Page<>(pageNum, pageSize),username,email,address));
    }



    //导出接口
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有的数据
        List<User> list = userService.list();
        //通过工具类创建writer 写出到磁盘路径

        //在内存操作，写出浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义依赖别名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("avatarUrl","头像");

        //一次性写出list内的对象到excel，使用默认格式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();;
        writer.close();

    }

    //导入接口
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        List<User> list = reader.readAll(User.class);
//        List<User> list = reader.read(0, 1, User.class);

        //忽略表头的中文，直接读取表的内容
        //表格头需按如下排列 【用户名 密码 昵称 邮箱 电话 地址 头像】
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for(List<Object> row : list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);

        }

        userService.saveBatch(users);
        return Result.success(true);

    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/password")       //  /user/password
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {

        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }





}


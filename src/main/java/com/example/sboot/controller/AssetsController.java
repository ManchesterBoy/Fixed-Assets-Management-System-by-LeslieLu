package com.example.sboot.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.entity.User;
import com.example.sboot.service.IUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import com.example.sboot.common.Result;

import com.example.sboot.service.IAssetsService;
import com.example.sboot.entity.Assets;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  资产管理控制器
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-16
 */
@RestController
@RequestMapping("/assets")
public class AssetsController {

    @Resource
    private IAssetsService assetsService;

    @Resource
    private IUserService userService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Assets assets){
        return Result.success(assetsService.saveOrUpdate(assets));
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(assetsService.removeById(id));
    }

    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(assetsService.removeByIds(ids));
    }

    //查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(assetsService.list());
    }



    //根据ID查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(assetsService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String content,
                           @RequestParam(defaultValue = "") String submitter) {
        QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        //单条件与多条件的模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        if(!"".equals(submitter)){
            queryWrapper.like("submitter",submitter);
        }


        return Result.success(assetsService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    //导出接口
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有的数据
        List<Assets> list = assetsService.list();
        //通过工具类创建writer 写出到磁盘路径

        //在内存操作，写出浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义依赖别名
        writer.addHeaderAlias("name","事件名称");
        writer.addHeaderAlias("content","具体内容");
        writer.addHeaderAlias("purchase","购置流程");
        writer.addHeaderAlias("accept","验收流程");
        writer.addHeaderAlias("allocation","调拨流程");
        writer.addHeaderAlias("management","处置流程");
        writer.addHeaderAlias("userId","事件提交者的用户id");
        writer.addHeaderAlias("price","申请金额");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("admin1","购置审批人的id");
        writer.addHeaderAlias("admin2","验收审批人的id");
        writer.addHeaderAlias("admin3","调拨审批人的id");
        writer.addHeaderAlias("admin4","处置审批人的id");


        //一次性写出list内的对象到excel，使用默认格式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("固定资产管理信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();;
        writer.close();

    }


    /**
     * purchase页面的分页查询
     */

    @GetMapping("/page/purchase")
    public Result findPagePurchase(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String content,
                           @RequestParam(defaultValue = "") String submitter) {
        QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
//        queryWrapper.eq("purchase","1");

        //单条件与多条件的模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        if(!"".equals(submitter)){
            queryWrapper.like("submitter",submitter);
        }

        Page<Assets> page = assetsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Assets> records = page.getRecords();
        for (Assets record : records) {
            User user = userService.getById(record.getUserId());
            if(user != null){
                record.setSubmitter(user.getNickname());
                record.setRole(user.getRole());
            }

            User adminUser1 = userService.getById(record.getAdmin1());    //获取购置审批人id对应的昵称与角色
            if(adminUser1 != null){
                record.setAdminPurchaseName(adminUser1.getNickname());
            }
        }


        return Result.success(page);
    }

    /**
     * 购置流程转向验收流程
     * @param id
     * @return
     */
    @PostMapping("/updateAccept/{id}/{admin1}")
    public Result updateAccept(@PathVariable Integer id,
                               @PathVariable Integer admin1){

        UpdateWrapper<Assets> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("purchase","2")
                .set("accept","1")
                .set("admin1",admin1)
                .eq("id",id);

        return Result.success(assetsService.update(updateWrapper));
    }


    /**
     * accept页面的分页查询
     */

    @GetMapping("/page/accept")
    public Result findPageAccept(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "") String content,
                                   @RequestParam(defaultValue = "") String submitter) {
        QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
//        queryWrapper.eq("accept","1");

        //单条件与多条件的模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        if(!"".equals(submitter)){
            queryWrapper.like("submitter",submitter);
        }

        Page<Assets> page = assetsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Assets> records = page.getRecords();
        for (Assets record : records) {
            User user = userService.getById(record.getUserId());
            if(user != null){
                record.setSubmitter(user.getNickname());
                record.setRole(user.getRole());
            }

            User adminUser2 = userService.getById(record.getAdmin2());    //获取验收审批人id对应的昵称与角色
            if(adminUser2 != null){
                record.setAdminAcceptName(adminUser2.getNickname());
            }
        }


        return Result.success(page);
    }

    /**
     * 验收流程转向调拨流程
     * @param id
     * @return
     */
    @PostMapping("/updateAllocation/{id}/{admin2}")
    public Result updateAllocation(@PathVariable Integer id,
                                   @PathVariable Integer admin2){

        UpdateWrapper<Assets> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("accept","2").
                set("allocation","1")
                .set("admin2",admin2)
                .eq("id",id);

        return Result.success(assetsService.update(updateWrapper));
    }


    /**
     * allocation页面的分页查询
     */

    @GetMapping("/page/allocation")
    public Result findPageAllocation(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String content,
                                 @RequestParam(defaultValue = "") String submitter) {
        QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
//        queryWrapper.eq("accept","1");

        //单条件与多条件的模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        if(!"".equals(submitter)){
            queryWrapper.like("submitter",submitter);
        }

        Page<Assets> page = assetsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Assets> records = page.getRecords();
        for (Assets record : records) {
            User user = userService.getById(record.getUserId());
            if(user != null){
                record.setSubmitter(user.getNickname());
                record.setRole(user.getRole());
            }

            User adminUser3 = userService.getById(record.getAdmin3());    //获取调拨审批人id对应的昵称与角色
            if(adminUser3 != null){
                record.setAdminAllocationName(adminUser3.getNickname());
            }
        }


        return Result.success(page);
    }

    /**
     * 调拨流程转向处置流程
     * @param id
     * @return
     */
    @PostMapping("/updateManagement/{id}/{admin3}")
    public Result updateManagement(@PathVariable Integer id,
                                   @PathVariable Integer admin3){

        UpdateWrapper<Assets> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("allocation","2")
                .set("management","1")
                .set("admin3",admin3)
                .eq("id",id);

        return Result.success(assetsService.update(updateWrapper));
    }

    /**
     * allocation页面的分页查询
     */

    @GetMapping("/page/management")
    public Result findPageManagement(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") String content,
                                     @RequestParam(defaultValue = "") String submitter) {
        QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
//        queryWrapper.eq("accept","1");

        //单条件与多条件的模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        if(!"".equals(submitter)){
            queryWrapper.like("submitter",submitter);
        }

        Page<Assets> page = assetsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Assets> records = page.getRecords();
        for (Assets record : records) {
            User user = userService.getById(record.getUserId());
            if(user != null){
                record.setSubmitter(user.getNickname());
                record.setRole(user.getRole());
            }

            User adminUser4 = userService.getById(record.getAdmin4());    //获取处置审批人id对应的昵称与角色
            if(adminUser4 != null){
                record.setAdminManagementName(adminUser4.getNickname());
            }
        }


        return Result.success(page);
    }

    /**
     * 处置流程结束
     * @param id
     * @return
     */
    @PostMapping("/updateFinal/{id}/{admin4}")
    public Result updateFinal(@PathVariable Integer id,
                              @PathVariable Integer admin4){

        UpdateWrapper<Assets> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("management","2")
                .set("admin4",admin4)
                .eq("id",id);

        return Result.success(assetsService.update(updateWrapper));
    }


    /**
     * account页面的分页查询
     */

    @GetMapping("/page/account")
    public Result findPageAccount(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") String content,
                                     @RequestParam(defaultValue = "") String submitter) {
        QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("management","2");

        //单条件与多条件的模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        if(!"".equals(submitter)){
            queryWrapper.like("submitter",submitter);
        }

        Page<Assets> page = assetsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Assets> records = page.getRecords();
        for (Assets record : records) {
            User user = userService.getById(record.getUserId());    //获取提交用户id对应的昵称与角色
            if(user != null){
                record.setSubmitter(user.getNickname());
                record.setRole(user.getRole());

            }

            User adminUser4 = userService.getById(record.getAdmin4());    //获取处置审批人id对应的昵称与角色
            if(adminUser4 != null){
                record.setAdminManagementName(adminUser4.getNickname());
            }

            User adminUser3 = userService.getById(record.getAdmin3());    //获取调拨审批人id对应的昵称与角色
            if(adminUser3 != null){
                record.setAdminAllocationName(adminUser3.getNickname());
            }

            User adminUser2 = userService.getById(record.getAdmin2());    //获取验收审批人id对应的昵称与角色
            if(adminUser2 != null){
                record.setAdminAcceptName(adminUser2.getNickname());
            }

            User adminUser1 = userService.getById(record.getAdmin1());    //获取购置审批人id对应的昵称与角色
            if(adminUser1 != null){
                record.setAdminPurchaseName(adminUser1.getNickname());
            }

        }


        return Result.success(page);
    }

}


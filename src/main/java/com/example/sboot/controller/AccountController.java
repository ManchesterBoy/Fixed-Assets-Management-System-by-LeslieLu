//package com.example.sboot.controller;
//
//import cn.hutool.poi.excel.ExcelUtil;
//import cn.hutool.poi.excel.ExcelWriter;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.sboot.entity.Assets;
//import com.example.sboot.entity.User;
//import com.example.sboot.service.IAssetsService;
//import com.example.sboot.service.IUserService;
//import org.springframework.web.bind.annotation.*;
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.net.URLEncoder;
//import java.util.List;
//import com.example.sboot.common.Result;
//
//import com.example.sboot.service.IAccountService;
//import com.example.sboot.entity.Account;
//
//
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author LeslieLu
// * @since 2023-02-17
// */
//@RestController
//@RequestMapping("/account")
//public class AccountController {
//
//    @Resource
//    private IAccountService accountService;
//
//    @Resource
//    private IUserService userService;
//
//    @Resource
//    private IAssetsService assetsService;
//
//    Account account;
//
//    //新增或者更新
//    @PostMapping
//    public Result save(@RequestBody Account account){
//        return Result.success(accountService.saveOrUpdate(account));
//    }
//
//
//    //删除
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        return Result.success(accountService.removeById(id));
//    }
//
//    //批量删除所选数据
//    @PostMapping("/del/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids){
//        return Result.success(accountService.removeByIds(ids));
//    }
//
//    //查询所有
//    @GetMapping
//    public Result findAll() {
//        return Result.success(accountService.list());
//    }
//
//    //根据ID查询
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(accountService.getById(id));
//    }
//
//    //分页查询
//    @GetMapping("/page")
//    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
//        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//
//        return Result.success(accountService.page(new Page<>(pageNum, pageSize),queryWrapper));
//    }
//
//    //分页查询 联表查询
//    @GetMapping("/page/account")
//    public Result findPages(@RequestParam Integer pageNum,
//                            @RequestParam Integer pageSize) {
////                            @RequestParam(defaultValue = "") String eventName,
////                            @RequestParam(defaultValue = "") String userName
//
//        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//
////        if(!"".equals(eventName)){
////            queryWrapper.like("eventName",accountService.getById());
////        }
////        if(!"".equals(userName)){
////            queryWrapper.like("eventName",username);
////        }
//
//        Page<Account> page = accountService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        List<Account> records = page.getRecords();
//        for (Account record : records) {
//            User user = userService.getById(record.getUserId());
//            Assets assets = assetsService.getById(record.getEventId());
//            if(user != null && assets != null){
//                    record.setUserName(user.getNickname());
//                    record.setRole(user.getRole());
//                    record.setEventName(assets.getName());
//                    record.setPrice(assets.getPrice());
//                    record.setManagementStatus(assets.getManagement());
//
//            }
//        }
//
//        return Result.success(page);
//    }
//
//
//    //导出接口
//    @GetMapping("/export")
//    public void export(HttpServletResponse response) throws Exception{
//        //从数据库查询出所有的数据
//        List<Account> list = accountService.list();
//        //通过工具类创建writer 写出到磁盘路径
//
//        //在内存操作，写出浏览器
//        ExcelWriter writer = ExcelUtil.getWriter(true);
//        //自定义依赖别名
//        writer.addHeaderAlias("id","台帐管理id");
//        writer.addHeaderAlias("eventId","业务管理事件id");
//        writer.addHeaderAlias("userId","所属用户id");
//        writer.addHeaderAlias("adminId","审批人id");
//        writer.addHeaderAlias("createTime","完成时间");
//
//
//        //一次性写出list内的对象到excel，使用默认格式，强制输出标题
//        writer.write(list,true);
//
//
//        //设置浏览器响应的格式
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        String fileName = URLEncoder.encode("台帐管理信息","UTF-8");
//        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");
//
//        ServletOutputStream out = response.getOutputStream();
//        writer.flush(out,true);
//        out.close();;
//        writer.close();
//
//    }
//
//
//    /**
//     * 处置流程结束,台帐管理生成数据
//     * @param id
//     * @return
//     */
//    @PostMapping("/updateAccount/{adminId}/{id}")
//    public Result updateAccount(@PathVariable Integer adminId,
//                                @PathVariable Integer id){
//
//
//
//        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.set("adminId",adminId).eq("id",id);
//
////        return Result.success(accountService.update(updateWrapper));
//        return Result.success(accountService.saveOrUpdate(account,updateWrapper));
//    }
//
//
//}
//

package com.example.sboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.entity.User;
import com.example.sboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody User user){
        //新增或更新
        return userService.saveUser(user);
    }

    //查询所有数据
    @GetMapping
    public List<User> index() {
        return userService.list();
    }

    //删除所选id的数据
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }


    //批量删除所选数据
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }


    //分页查询
    //接口路径 /user/page
    //@RequstParamJIESHOU1 pageNum=1 & pageSize=10
//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum,
//                                       @RequestParam Integer pageSize,
//                                       @RequestParam String username){
//        pageNum = (pageNum - 1) * pageSize;
//        username = "%" + username + "%";
//        List<User> data = userMapper.selectPage(pageNum,pageSize,username);
//        Integer total = userMapper.selectTotal(username);
//        Map<String,Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//    }

    //分页查询 mybatis-plus方式
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address){

        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByDesc("id");

        return userService.page(page,queryWrapper);

    }


}

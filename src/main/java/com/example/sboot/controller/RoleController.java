package com.example.sboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.sboot.service.IRoleService;
import com.example.sboot.entity.Role;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  角色管理控制器
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Role role){
        return Result.success(roleService.saveOrUpdate(role));
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(roleService.removeByIds(ids));
    }

    //查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //根据ID查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        //单条件模糊查询
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }

        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    /**
     * 绑定角色和菜单的关系
     * @param roleId    角色ID
     * @param menuIds   菜单ID数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }


    @GetMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId){

        return Result.success(roleService.setRoleMenu(roleId));
    }


}


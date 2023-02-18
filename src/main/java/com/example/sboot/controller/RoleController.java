package com.example.sboot.controller;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Constants;
import com.example.sboot.common.RedisClearCache;
import com.example.sboot.common.Result;
import com.example.sboot.entity.Menu;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisClearCache redisClearCache;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Role role){

        roleService.saveOrUpdate(role);
        redisClearCache.flushRedis(Constants.ROLE_KEY);     //执行完增删改操作后，需删除Redis里面的缓存
        return Result.success();
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {

        roleService.removeById(id);
        redisClearCache.flushRedis(Constants.ROLE_KEY);     //执行完增删改操作后，需删除Redis里面的缓存
        return Result.success();
    }

    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){

        roleService.removeByIds(ids);
        redisClearCache.flushRedis(Constants.ROLE_KEY);     //执行完增删改操作后，需删除Redis里面的缓存
        return Result.success();
    }


    //查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //实现redis缓存
    @GetMapping("/roles")
    public Result findAlls(@RequestParam String name) {

        /**
         * redis减轻数据库压力
         */
        //1.从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.ROLE_KEY);
        List<Role> role;
        if(StrUtil.isBlank(jsonStr)){   //2.取出来的json是空的
            role = roleService.findRole(name);      //3.从数据库取出数据
        //4.在去缓存到redis
            stringRedisTemplate.opsForValue().set(Constants.ROLE_KEY, JSONUtil.toJsonPrettyStr(role));
        }else{
            //5.如果有，从redis缓存中获取数据
            role = JSONUtil.toBean(jsonStr, new TypeReference<List<Role>>() {
            }, true);
        }

        return Result.success(role);

//        return Result.success(roleService.list());
//        return  Result.success(roleService.findRole(name));
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


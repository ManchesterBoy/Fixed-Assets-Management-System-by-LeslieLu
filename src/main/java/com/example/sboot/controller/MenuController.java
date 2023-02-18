package com.example.sboot.controller;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Constants;
import com.example.sboot.common.RedisClearCache;
import com.example.sboot.entity.Dict;
import com.example.sboot.mapper.DictMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.example.sboot.common.Result;

import com.example.sboot.service.IMenuService;
import com.example.sboot.entity.Menu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  菜单管理控制器
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisClearCache redisClearCache;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu){

        menuService.saveOrUpdate(menu);
        redisClearCache.flushRedis(Constants.MENU_KEY);     //执行完增删改操作后，需删除Redis里面的缓存
        return Result.success();
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {

        menuService.removeById(id);
        redisClearCache.flushRedis(Constants.MENU_KEY);     //执行完增删改操作后，需删除Redis里面的缓存
        return Result.success();
    }

    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){

        menuService.removeByIds(ids);
        redisClearCache.flushRedis(Constants.MENU_KEY);     //执行完增删改操作后，需删除Redis里面的缓存
        return Result.success();
    }

    @GetMapping("/ids")
    public Result findAllIds(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    //查询所有
    //实现redis缓存
    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {

        /**
         * redis减轻数据库压力
         */
        //1.从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.MENU_KEY);
        List<Menu> menus;
        if(StrUtil.isBlank(jsonStr)){   //2.取出来的json是空的
            menus = menuService.findMenus(name);//3.从数据库取出数据
//4.在去缓存到redis
            stringRedisTemplate.opsForValue().set(Constants.MENU_KEY,JSONUtil.toJsonPrettyStr(menus));
        }else{
            //5.如果有，从redis缓存中获取数据
            menus = JSONUtil.toBean(jsonStr, new TypeReference<List<Menu>>() {
            }, true);
        }

        return Result.success(menus);
//        return Result.success(menuService.findMenus(name));
    }

    //根据ID查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }


    @GetMapping("/icons")
    public Result getIcons() {

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(null));
    }

    //分页查询
//    @GetMapping("/page")
//    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
//                           @RequestParam(defaultValue = "") String name) {
//        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//
//        //单条件模糊查询
//        if(!"".equals(name)){
//            queryWrapper.like("name",name);
//        }
//
//        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
//    }



}


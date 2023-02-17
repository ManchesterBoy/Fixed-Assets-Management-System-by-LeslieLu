package com.example.sboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Constants;
import com.example.sboot.entity.Dict;
import com.example.sboot.mapper.DictMapper;
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

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu){
        return Result.success(menuService.saveOrUpdate(menu));
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    //批量删除所选数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(menuService.removeByIds(ids));
    }

    @GetMapping("/ids")
    public Result findAllIds(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    //查询所有
    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findMenus(name));
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


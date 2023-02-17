package com.example.sboot.service;

import com.example.sboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  菜单管理服务类
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}

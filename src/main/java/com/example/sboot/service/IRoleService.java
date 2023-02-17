package com.example.sboot.service;

import com.example.sboot.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  角色管理服务类
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> setRoleMenu(Integer roleId);
}

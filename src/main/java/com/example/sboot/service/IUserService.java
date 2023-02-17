package com.example.sboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.controller.dto.UserDTO;
import com.example.sboot.controller.dto.UserPasswordDTO;
import com.example.sboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  用户管理服务类
 * </p>
 *
 * @author LeslieLu
 * @since 2023-01-28
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    Page<User> findPage(Page<User> objectPage, String username, String email, String address);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}

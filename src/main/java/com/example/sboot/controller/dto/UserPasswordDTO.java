package com.example.sboot.controller.dto;

import lombok.Data;

/**
 * 修改密码封装实体
 * @author LeslieLu
 */

@Data
public class UserPasswordDTO {

    private String username;
    private String phone;
    private String password;
    private String newPassword;

}

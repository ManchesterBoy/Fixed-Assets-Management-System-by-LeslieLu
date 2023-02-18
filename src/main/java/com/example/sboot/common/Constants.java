package com.example.sboot.common;

/**
 * 对应数字的报错提示
 * @author LeslieLu
 */
public interface Constants {

    String CODE_200 = "200";    //成功
    String CODE_500 = "500";    //系统错误
    String CODE_401 = "401";    //权限不足
    String CODE_400 = "400";    //参数错误不足
    String CODE_600 = "600";    //其他业务异常

    String DICT_TYPE_ICON = "icon";

    //在RoleEnum
//    String ROLE_ADMIN = "ROLE_ADMIN";   //角色为管理员
//    String ROLE_USER = "ROLE_USER";     ////角色为用户

//    String FILES_KEY = "FILES_MANAGE";   //文件管理的redis字符缓存
//    String HOME_KEY_1 = "HOME_MANAGE_1";     //主页管理的redis缓存
//    String USER_KEY = "USER_MANAGE";     //用户管理的redis缓存
    String ROLE_KEY = "ROLE_MANAGE";     //角色管理的redis缓存
    String MENU_KEY = "MENU_MANAGE";     //菜单管理的redis缓存
//    String BUSINESS_KEY = "BUSINESS_MANAGE";     //业务管理的redis缓存


}

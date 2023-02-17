package com.example.sboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sboot.common.Constants;
import com.example.sboot.controller.dto.UserDTO;
import com.example.sboot.controller.dto.UserPasswordDTO;
import com.example.sboot.entity.Menu;
import com.example.sboot.entity.User;
import com.example.sboot.exception.ServiceException;
import com.example.sboot.mapper.RoleMapper;
import com.example.sboot.mapper.RoleMenuMapper;
import com.example.sboot.mapper.UserMapper;
import com.example.sboot.service.IMenuService;
import com.example.sboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeslieLu
 * @since 2023-01-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Resource
//    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",userDTO.getUsername());
//        queryWrapper.eq("password",userDTO.getPassword());

        //遇到用户名与密码都相同，只要不为空都统一放行
        /**
         * 不知道为什么前台页面输错误用户名与密码时，后台查询数据库，符合个数为0即null时，依然是返回true???
         **/
//        List<User> list = list(queryWrapper);
//        return list != null;

        User one = getUserInfo(userDTO);
        if(one != null){
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();    //ROLE_ADMIN
            //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }
        else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }


    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);      //把copy完之后的用户对象存储到数据库
        }
        else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }

        return one;
    }

    @Override
    public Page<User> findPage(Page<User> page, String username, String email, String address) {

        return userMapper.findPage(page,username,email,address);

    }

    //实现修改密码
    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {

        int update = userMapper.updatePassword(userPasswordDTO);
        if(update < 1){
            throw new ServiceException(Constants.CODE_600,"密码错误");
        }

    }

    private User getUserInfo(UserDTO userDTO){      //查询用户名与密码的方法封装
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        //遇到用户名与密码都相同，都返回false，不允许通过
        try{
            one = getOne(queryWrapper);    //从数据库查询用户信息
        } catch (Exception e){
            //查出多个
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     * @param  "roleId"
     * @return
     */
    private List<Menu> getRoleMenus(String  roleFlag){

        Integer roleId = roleMapper.selectByFlag(roleFlag);
        //当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        //查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();

        //筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf 移除children里面不再menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));

        }
        return roleMenus;
    }

}

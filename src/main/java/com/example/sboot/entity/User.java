package com.example.sboot.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author LeslieLu
 * @since 2023-01-28
 */
@Getter
@Setter
@TableName("sys_user")
@ToString
//@ApiModel(value = "User对象",description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * ID	
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户名
     */
      private String username;

      /**
     * 密码
     */
      @JsonIgnore   //隐藏密码
      private String password;

      /**
     * 昵称
     */
      private String nickname;

      /**
     * 邮箱
     */
      private String email;

      /**
     * 电话
     */
      private String phone;

      /**
     * 地址
     */
      private String address;

      /**
     * 创建时间
     */
      private Date createTime;

      /**
     * 头像
     */
      private String avatarUrl;

      @TableLogic   //逻辑删除
      private Integer deleted;

//      @ApiModelProperty("角色")
      private String  role;   //角色

//      @TableField(exist = false)
//      private List<Course> courses;
//
//      @TableField(exist = false)
//      private List<Course> stuCourses;

}

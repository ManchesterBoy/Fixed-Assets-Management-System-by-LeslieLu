package com.example.sboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
@Getter
@Setter
  @TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 角色id
     */
        private Integer roleId;

      /**
     * 菜单id
     */
      private Integer menuId;


}

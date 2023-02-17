package com.example.sboot.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单实体
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
@Getter
@Setter
  @TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 名称
     */
      private String name;

      /**
     * 路径
     */
      private String path;

      /**
     * 图标
     */
      private String icon;

      /**
     * 描述
     */
      private String description;

      /**
     * 逻辑删除 0为未删除 1为已删除
     */
      @TableLogic
      private Integer deleted;

      @TableField(exist = false)
      private List<Menu> children;

      private Integer pid;

      private String pagePath;

}

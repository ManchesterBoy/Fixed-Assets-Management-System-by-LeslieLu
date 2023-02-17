package com.example.sboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色实体
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
@Getter
@Setter
  @TableName("sys_role")
public class Role implements Serializable {

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
     * 描述
     */
      private String description;

      /**
       * 逻辑删除 0为未删除 1为已删除
       */
      @TableLogic
      private Integer deleted;

      private String flag;   //唯一标识

}

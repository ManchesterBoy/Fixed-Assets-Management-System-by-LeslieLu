package com.example.sboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 图标实体
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-13
 */
@Getter
@Setter
  @TableName("sys_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 名称
     */
      private String name;

      /**
     * 内容
     */
      private String value;

      /**
     * 类型
     */
      private String type;


}

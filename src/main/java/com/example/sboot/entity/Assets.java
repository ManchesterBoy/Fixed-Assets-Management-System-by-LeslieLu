package com.example.sboot.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 固定资产实体
 * </p>
 *
 * @author LeslieLu
 * @since 2023-02-16
 */
@Getter
@Setter
  @TableName("sys_assets")
public class Assets implements Serializable {

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
     * 具体内容
     */
      private String content;

      /**
     * 购置流程
       * 0为未审批 1为待审批 2为已审批
     */
      private Integer purchase;

      /**
     * 验收流程
       * 0为未审批 1为待审批 2为已审批
     */
      private Integer accept;

      /**
     * 调拨流程
       * 0为未审批 1为待审批 2为已审批
     */
      private Integer allocation;

      /**
     * 处置流程
       * 0为未审批 1为待审批 2为已审批
     */
      private Integer management;

      /**
     * 逻辑删除 0为未删除 1为已删除
     */
      @TableLogic
      private Integer deleted;

      @TableField(exist = false)
      private String submitter;     //提交者名字

    /**
     * 事件提交用户的id
     */
    private String userId;
    /**
     * 处置审批人的id
     */
    private String admin4;

    /**
     * 调拨审批人的id
     */
    private String admin3;

    /**
     * 验收审批人的id
     */
    private String admin2;

    /**
     * 购置审批人的id
     */
    private String admin1;



    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 申请金额
     */
    private Integer price;


    @TableField(exist = false)
    private String  role;       //提交者的角色权限

    @TableField(exist = false)
    private String adminManagementName;     //处置审批人的名称

    @TableField(exist = false)
    private String adminAllocationName;     //调拨审批人的名称

    @TableField(exist = false)
    private String adminAcceptName;     //验收审批人的名称

    @TableField(exist = false)
    private String adminPurchaseName;     //购置审批人的名称


}

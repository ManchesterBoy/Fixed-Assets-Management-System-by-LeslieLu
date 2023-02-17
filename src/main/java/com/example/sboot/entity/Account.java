//package com.example.sboot.entity;
//
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableLogic;
//import com.baomidou.mybatisplus.annotation.TableName;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * <p>
// *
// * </p>
// *
// * @author LeslieLu
// * @since 2023-02-17
// */
//@Getter
//@Setter
//  @TableName("sys_account")
//public class Account implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//      /**
//     * 台帐管理id
//     */
//        private Integer id;
//
//      /**
//     * 资产管理中的事件id
//     */
//      private Integer eventId;
//
//      /**
//     * 事件处理完毕的时间
//     */
//      private LocalDateTime createTime;
//
//      /**
//     * 逻辑删除 0为未删除 1为已删除
//     */
//      @TableLogic
//      private Integer deleted;
//
//      /**
//     * 当前用户的id
//     */
//      private Integer userId;
//
//      /**
//       * 审批人的id
//       */
//      private Integer adminId;
//
//      @TableField(exist = false)
//      private String userName;
//
//      @TableField(exist = false)
//      private String eventName;
//
//      @TableField(exist = false)
//      private String role;
//
//      @TableField(exist = false)
//      private Integer price;
//
//      @TableField(exist = false)
//      private Integer managementStatus;
//
//
//}

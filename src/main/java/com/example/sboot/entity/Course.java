//package com.example.sboot.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import java.io.Serializable;
//
//import com.baomidou.mybatisplus.annotation.TableLogic;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * <p>
// *不能注释掉，否则后端无法启动（只需注释UserMapper关于Course的代码即可无视Course实体）
// * </p>
// *
// * @author LeslieLu
// * @since 2023-02-15
// */
//@Getter
//@Setter
//  public class Course implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//      /**
//     * id
//     */
//        @TableId(value = "id", type = IdType.AUTO)
//      private Integer id;
//
//      /**
//     * 课程名称
//     */
//      private String name;
//
//      /**
//     * 学分
//     */
//      private Integer score;
//
//      /**
//     * 上课时间
//     */
//      private String times;
//
//      /**
//     * 是否开课
//     */
//      private Boolean state;
//
//      /**
//     * 授课老师id
//     */
//      private Integer teacherId;
//
//      /**
//     * 逻辑删除 0为未删除 1为已删除
//     */
//      @TableLogic
//      private Integer deleted;
//
//      @TableField(exist = false)
//      private String teacher;
//
//
//}

//package com.example.sboot.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.sboot.entity.User;
//import com.example.sboot.service.IUserService;
//import org.springframework.web.bind.annotation.*;
//import javax.annotation.Resource;
//import java.util.List;
//import com.example.sboot.common.Result;
//
//import com.example.sboot.service.ICourseService;
//import com.example.sboot.entity.Course;
//
//
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author LeslieLu
// * @since 2023-02-15
// */
//@RestController
//@RequestMapping("/course")
//public class CourseController {
//
//    @Resource
//    private ICourseService courseService;
//
//    @Resource
//    private IUserService userService;
//
//    //新增或者更新
//    @PostMapping
//    public Result save(@RequestBody Course course){
//        return Result.success(courseService.saveOrUpdate(course));
//    }
//
//    @PostMapping("/studentCourse/{courseId}/{studentId}")
//    public Result studentCourse(@PathVariable Integer courseId,@PathVariable Integer studentId){
//        courseService.setStudentCourse(courseId,studentId);
//        return Result.success();
//    }
//
//
//    //删除
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        return Result.success(courseService.removeById(id));
//    }
//
//    //批量删除所选数据
//    @PostMapping("/del/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids){
//        return Result.success(courseService.removeByIds(ids));
//    }
//
//    //查询所有
//    @GetMapping
//    public Result findAll() {
//        return Result.success(courseService.list());
//    }
//
//    //根据ID查询
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(courseService.getById(id));
//    }
//
//    //分页查询
//    @GetMapping("/page")
//    public Result findPage(@RequestParam String name,@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
//        //第一种方法实现 一对一 联表查询
////        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
////        queryWrapper.orderByDesc("id");
////
////        Page<Course> page = courseService.page(new Page<>(pageNum, pageSize), queryWrapper);
////        List<Course> records = page.getRecords();
////        for (Course record : records) {
////            User user = userService.getById(record.getTeacherId());
////            if(user != null){
////                record.setTeacher(user.getNickname());
////            }
////        }
//
//        Page<Course> page = courseService.findPage(new Page<>(pageNum, pageSize), name);
//
//        return Result.success(page);
//    }
//
//
//
//
//
//}
//

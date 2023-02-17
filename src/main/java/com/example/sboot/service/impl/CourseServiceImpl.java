//package com.example.sboot.service.impl;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.sboot.entity.Course;
//import com.example.sboot.mapper.CourseMapper;
//import com.example.sboot.service.ICourseService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//
///**
// * <p>
// *  服务实现类
// * </p>
// *
// * @author LeslieLu
// * @since 2023-02-15
// */
//@Service
//public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
//
//    @Resource
//    private CourseMapper courseMapper;
//
//    @Override
//    public Page<Course> findPage(Page<Course> page, String name) {
//        return courseMapper.findPage(page,name);
//    }
//
//    @Transactional
//    @Override
//    public void setStudentCourse(Integer courseId, Integer studentId) {
//
//        courseMapper.deleteStudentCourse(courseId,studentId);
//        courseMapper.setStudentCourse(courseId,studentId);
//
//    }
//}

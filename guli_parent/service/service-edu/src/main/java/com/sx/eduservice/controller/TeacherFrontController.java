package com.sx.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduCourse;
import com.sx.eduservice.entity.EduTeacher;
import com.sx.eduservice.service.EduCourseService;
import com.sx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //1 分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页所有数据
        return Result.ok().data(map);
    }
    //2 讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);
        return Result.ok().data("teacher",eduTeacher).data("courseList",list);
    }
}

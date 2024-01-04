package com.sx.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;

import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduCourse;
import com.sx.eduservice.entity.vo.CourseInfoVo;
import com.sx.eduservice.entity.vo.CoursePublishVo;
import com.sx.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程(EduCourse)表控制层
 *
 * @author makejava
 * @since 2023-11-02 17:54:25
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController extends ApiController {
    @Autowired
    private EduCourseService courseService;

    // 1. 添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.addCourseInfo(courseInfoVo);
        return Result.ok().data("id", id);
    }

    //查询课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return Result.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        courseService.publishCourse(id);
        return Result.ok();
    }

    //查询全部课程
    @GetMapping("getAllCourse")
    public Result getAllCourse() {
        List<EduCourse> list = courseService.list(null);
        return Result.ok().data("list", list);
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return Result.ok();
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    public Result pageCourseCondition(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) EduCourse course) {
        IPage<EduCourse>  page=  courseService.pageCourseCondition(current, limit, course);
        return Result.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

}


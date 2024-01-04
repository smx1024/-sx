package com.sx.eduservice.controller;

import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduCourse;
import com.sx.eduservice.entity.EduTeacher;
import com.sx.eduservice.service.EduCourseService;
import com.sx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    //查询前8条热门课程，查询前4条名师
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("index")
    public Result index(){
        //查询前8条热门课程
        List<EduCourse> eduList = courseService.selectHotCourse();
        //查询前4条名师
        List<EduTeacher> teacherList = teacherService.selectHotTeacher();
        return Result.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}

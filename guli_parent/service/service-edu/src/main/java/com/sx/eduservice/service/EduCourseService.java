package com.sx.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sx.eduservice.entity.EduCourse;
import com.sx.eduservice.entity.vo.CourseFrontVo;
import com.sx.eduservice.entity.vo.CourseInfoVo;
import com.sx.eduservice.entity.vo.CoursePublishVo;
import com.sx.eduservice.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;


/**
 * 课程(EduCourse)表服务接口
 *
 * @author makejava
 * @since 2023-11-02 17:54:28
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void publishCourse(String id);

    void removeCourse(String courseId);

    IPage<EduCourse> pageCourseCondition(long current, long limit, EduCourse course);

    List<EduCourse> selectHotCourse();

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}


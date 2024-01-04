package com.sx.eduservice.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sx.eduservice.entity.EduCourse;
import com.sx.eduservice.entity.vo.CourseFrontVo;
import com.sx.eduservice.entity.vo.CoursePublishVo;
import com.sx.eduservice.entity.vo.CourseWebVo;
import org.apache.ibatis.annotations.Param;


/**
 * 课程(EduCourse)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-02 17:54:27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

 public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}


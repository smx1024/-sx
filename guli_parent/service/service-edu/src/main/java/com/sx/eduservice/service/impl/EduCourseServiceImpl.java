package com.sx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sx.eduservice.entity.EduCourse;

import com.sx.eduservice.entity.EduCourseDescription;
import com.sx.eduservice.entity.vo.CourseFrontVo;
import com.sx.eduservice.entity.vo.CourseInfoVo;
import com.sx.eduservice.entity.vo.CoursePublishVo;
import com.sx.eduservice.entity.vo.CourseWebVo;
import com.sx.eduservice.mapper.EduCourseMapper;
import com.sx.eduservice.service.EduChapterService;
import com.sx.eduservice.service.EduCourseDescriptionService;
import com.sx.eduservice.service.EduCourseService;
import com.sx.eduservice.service.EduVideoService;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程(EduCourse)表服务实现类
 *
 * @author makejava
 * @since 2023-11-02 17:54:29
 */
@Service("eduCourseService")
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            // 添加失败
            throw new GuliException(20001, "添加课程信息失败");
        }

        //想课程简介表添加数据
        String cid = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);

        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse course = getById(courseId);
        //查询描述表
        EduCourseDescription description = courseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        courseInfoVo.setDescription(description.getDescription());
        BeanUtils.copyProperties(course, courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = baseMapper.updateById(course);
        if (i == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = this.baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void publishCourse(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        int i = baseMapper.updateById(course);
        if (i == 0) {
            throw new GuliException(20001, "发布课程失败");
        }
    }

    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);
        //根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);
        //根据课程id删除描述
        boolean b = courseDescriptionService.removeById(courseId);
        if (!b) {
            throw new GuliException(20001, "删除失败");
        }
        //根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    public IPage<EduCourse> pageCourseCondition(long current, long limit, EduCourse course) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (course != null) {
            String title = course.getTitle();
            String status = course.getStatus();
            Date gmtCreate = course.getGmtCreate();
            Date gmtModified = course.getGmtModified();
            if (title != null) {
                wrapper.like("title", title);
            }
            if (status != null) {
                wrapper.eq("status", status);
            }
            if (gmtCreate != null) {
                wrapper.eq("gmt_create", gmtCreate);
            }
            if (gmtModified != null) {
                wrapper.eq("gmt_modified", gmtModified);
            }
        }
        wrapper.orderByDesc("gmt_create");
        Page<EduCourse> objectPage = new Page<>(current, limit);
        IPage<EduCourse> page = this.page(objectPage, wrapper);
        return page;
    }

    @Override
    public List<EduCourse> selectHotCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> list = list(wrapper);
        return list;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        String title = null;
        String subjectId = null;
        String subjectParentId = null;
        String gmtCreateSort = null;
        String buyCountSort = null;
        String priceSort = null;
        String teacherId = null;

        if (!StringUtils.isEmpty(courseFrontVo)){
            title = courseFrontVo.getTitle();
            subjectId = courseFrontVo.getSubjectId();
            subjectParentId = courseFrontVo.getSubjectParentId();
            gmtCreateSort = courseFrontVo.getGmtCreateSort();
            buyCountSort = courseFrontVo.getBuyCountSort();
            priceSort = courseFrontVo.getPriceSort();
            teacherId = courseFrontVo.getTeacherId();
        }


        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接条件
        if (!StringUtils.isEmpty(subjectParentId)){//一级分类
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){//二级分类
            wrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)){//关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(priceSort)){//价格
            wrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){//最新，创建时间
            wrapper.orderByDesc("gmt_create");
        }


        baseMapper.selectPage(pageCourse, wrapper);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> courseList = pageCourse.getRecords();//数据集合
        long size = pageCourse.getSize();//每页记录数
        long current = pageCourse.getCurrent();//当前页
        long pages = pageCourse.getPages();//总页数
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页
        boolean hasNext = pageCourse.hasNext();//是否有下一页

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",courseList);
        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);

        return map;

    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        CourseWebVo courseWebVo = baseMapper.getBaseCourseInfo(courseId);
        return courseWebVo;
    }
}


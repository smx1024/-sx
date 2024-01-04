package com.sx.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.commonutils.JwtUtils;
import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduCourse;
import com.sx.eduservice.entity.vo.ChapterVo;
import com.sx.eduservice.entity.vo.CourseFrontVo;
import com.sx.eduservice.entity.vo.CourseWebVo;
import com.sx.eduservice.entity.vo.EduCourseVo;
import com.sx.eduservice.fegin.OrderClient;
import com.sx.eduservice.service.EduChapterService;
import com.sx.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService  chapterService;
    @Autowired
    private OrderClient orderClient;
    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        //返回分页所有数据
        return Result.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId,HttpServletRequest request){
        boolean isBuyCourse = false;
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo coursewebvo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideo = chapterService.getChapterVideo(courseId);
        //判断课程是否购买
        //获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(memberId)){
            //根据课程id、用户id，查询课程是否已经购买
            isBuyCourse = orderClient.isBuyCourse(memberId, courseId);
        }
        return Result.ok().data("courseWebVo", coursewebvo).data("chapterVideoList", chapterVideo).data("isBuy",isBuyCourse);
    }

    @GetMapping("getCourseInfoOrder/{id}")
    public  EduCourseVo getCourseInfoOrder(@PathVariable String id){
        EduCourse eduCourse = courseService.getById(id);
        EduCourseVo eduCourseVo = new EduCourseVo();
        BeanUtils.copyProperties(eduCourse,eduCourseVo);
        return  eduCourseVo;
    }
}

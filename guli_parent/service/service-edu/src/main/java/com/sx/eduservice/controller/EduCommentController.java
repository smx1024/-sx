package com.sx.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.commonutils.JwtUtils;
import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduComment;
import com.sx.eduservice.service.EduCommentService;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/educomment")
public class EduCommentController {
    @Autowired
     private EduCommentService eduCommentService;
//添加评论
    @PostMapping("addComment")
    public Result addComment(@RequestBody EduComment eduComment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(memberId==null){
            throw  new GuliException(20001,"请先登录");
        }
        System.out.println(request.toString());
        eduCommentService.save(eduComment,memberId);
        return Result.ok();
    }
    //分页查询评论
    @GetMapping("getCommentList/{page}/{limit}")
    public Result getCommentList(@PathVariable long page,@PathVariable long limit,String courseId){
        Map<String, Object> commentList = eduCommentService.getCommentList(page, limit, courseId);
        return  Result.ok().data(commentList);
    }

}


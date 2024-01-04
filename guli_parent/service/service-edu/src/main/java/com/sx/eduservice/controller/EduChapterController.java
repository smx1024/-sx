package com.sx.eduservice.controller;


import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduChapter;
import com.sx.eduservice.entity.vo.ChapterVo;
import com.sx.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-02
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list = chapterService.getChapterVideo(courseId);
        return Result.ok().data("list",list);
    }
    //添加章节
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return Result.ok();
    }
    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter",eduChapter);
    }
    //修改章节
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.ok();
    }
    //删除章节
    @DeleteMapping("{chapterId}")
    public Result deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean flag = chapterService.deleteCxhapter(chapterId);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}


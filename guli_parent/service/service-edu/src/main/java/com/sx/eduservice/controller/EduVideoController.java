package com.sx.eduservice.controller;


import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduVideo;
import com.sx.eduservice.fegin.VodClient;
import com.sx.eduservice.service.EduVideoService;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-02
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private  VodClient vodClient;
        //1.添加小节
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return Result.ok();
    }
        //2.删除小节
    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(videoSourceId!=null){
            Result result = vodClient.removeAliyunVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return Result.ok();
    }
        //3.修改小节
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return Result.ok();
    }
        //4.根据id查询小节
    @GetMapping("getVideoInfo/{id}")
    public Result getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return Result.ok().data("video",eduVideo);
    }



}


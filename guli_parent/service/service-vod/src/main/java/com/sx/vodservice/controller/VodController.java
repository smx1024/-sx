package com.sx.vodservice.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.sx.commonutils.Result;
import com.sx.serivcebase.exception.GuliException;
import com.sx.vodservice.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file) {
        //返回时品id
        String id = vodService.uploadAliyunVideo(file);
        return Result.ok().data("videoId", id);
    }

    //获取上传视频的凭证和地址
    //删除阿里云视频
    @DeleteMapping("removeAliyunVideo/{id}")
    public Result removeAliyunVideo(@PathVariable String id) {
        vodService.removeAliyunVideo(id);
        return Result.ok();
    }
    //删除多个阿里云视频
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAliyunVideo(videoIdList);
        return Result.ok();
    }
    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try {
            String playAuth = vodService.getPlayAuth(id);
            return Result.ok().data("PlayAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取视频凭证失败");
        }

    }

}

package com.sx.eduservice.fegin;

import com.sx.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    //删除单个视频
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    public Result removeAliyunVideo(@PathVariable String id);
    //删除多个视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}

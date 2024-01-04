package com.sx.ossserivce.controller;

import com.sx.commonutils.Result;
import com.sx.ossserivce.service.OssSerivce;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@CrossOrigin
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssSerivce ossSerivce;
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
     String url= ossSerivce.uploadFile(file);
        return Result.ok().data("url",url);
    }

}

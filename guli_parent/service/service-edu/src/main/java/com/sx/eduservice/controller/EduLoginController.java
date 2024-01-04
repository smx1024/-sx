package com.sx.eduservice.controller;

import com.sx.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","admin");
    }
    //info
    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
    }
}

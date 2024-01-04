package com.sx.Userservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.sx.Userservice.entity.UcenterMember;
import com.sx.Userservice.entity.vo.RegisterVo;
import com.sx.Userservice.service.UcenterMemberService;
import com.sx.commonutils.JwtUtils;
import com.sx.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-08
 */
@RestController
//@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;
    //登录
    @PostMapping("login")
    public Result login(@RequestBody UcenterMember member){
       String token= memberService.login(member);
        return Result.ok().data("token",token);
    }
    //注册
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return Result.ok().data("memberInfo",member);
    }
    @GetMapping("getMemberInfoById/{id}")
    public UcenterMember getMemberInfoById(@PathVariable("id") String id){
        UcenterMember member = memberService.getById(id);
        return member;
    }

    //根据日期，获取那天注册人数
    @GetMapping("/countRegister/{day}")
    public Result countRegister(@PathVariable String day){
        Integer count = memberService.getCountRegister(day);
        return Result.ok().data("countRegister",count);
    }

}


package com.sx.cmsservice.controller;

import com.sx.cmsservice.entity.CrmBanner;
import com.sx.cmsservice.service.CrmBannerService;
import com.sx.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerFrontService;
    //1.查询所有banner
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        List<CrmBanner> list = bannerFrontService.getAllBanner();
        return Result.ok().data("list",list);

    }
}

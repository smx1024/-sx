package com.sx.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.cmsservice.entity.CrmBanner;
import com.sx.cmsservice.service.CrmBannerService;
import com.sx.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import javax.jws.Oneway;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-07
 */
@RestController
//@CrossOrigin
@RequestMapping("/educms/bannerAdmin")
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;
        //1.分页查询banner

    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable long page, @PathVariable long limit){
        bannerService.pageBanner(page,limit);
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        bannerService.page(bannerPage, null);
        return Result.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }
        //2.添加banner
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return Result.ok();
    }
        //3.修改banner
    @PostMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return Result.ok();
    }
        //4.删除banner
    @DeleteMapping("removeBanner/{id}")
    public Result removeBanner(@PathVariable String id){
        bannerService.removeById(id);
        return Result.ok();
    }
}


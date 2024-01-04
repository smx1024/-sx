package com.sx.staservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.sx.commonutils.Result;
import com.sx.staservice.entity.StatisticsDaily;
import com.sx.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-15
 */
@RestController
//@CrossOrigin
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;
    //统计某一天注册人数,生成统计数据
    @GetMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return Result.ok();
    }
    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String, Object> map = statisticsDailyService.getShowData(type, begin, end);
        return Result.ok().data(map);
    }
}


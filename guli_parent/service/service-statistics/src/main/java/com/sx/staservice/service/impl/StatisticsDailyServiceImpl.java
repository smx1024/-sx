package com.sx.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.commonutils.Result;
import com.sx.staservice.entity.StatisticsDaily;
import com.sx.staservice.fegin.UserFegin;
import com.sx.staservice.mapper.StatisticsDailyMapper;
import com.sx.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-15
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UserFegin userFegin;

    @Override
    public void registerCount(String day) {

        //添加之前先删除表相同的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        Result result = userFegin.countRegister(day);
        Integer count = (Integer) result.getData().get("countRegister");

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated",begin,end);

        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        //前端要求数组json结果，对应后端为List集合
        //创建两个list集合，一个放日期X轴，一个放数量Y轴
        List<String> xlist = new ArrayList<>();
        List<Integer> ylist = new ArrayList<>();

        for (StatisticsDaily daily : dailyList) {
            xlist.add(daily.getDateCalculated());

            //判断查询的哪个字段
            if ("register_num".equals(type)){
                ylist.add(daily.getRegisterNum());
            }
            if ("login_num".equals(type)){
                ylist.add(daily.getLoginNum());
            }
            if ("video_view_num".equals(type)){
                ylist.add(daily.getVideoViewNum());
            }
            if ("course_num".equals(type)){
                ylist.add(daily.getCourseNum());
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xlist",xlist);
        map.put("ylist",ylist);

        return map;

    }
}

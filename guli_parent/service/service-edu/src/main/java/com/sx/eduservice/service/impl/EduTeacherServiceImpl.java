package com.sx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.eduservice.entity.EduTeacher;
import com.sx.eduservice.mapper.EduTeacherMapper;
import com.sx.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-10-28
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public List<EduTeacher> selectHotTeacher() {
        // 查询前四条名师
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<EduTeacher>().orderByDesc("id").last("limit 4");
        List<EduTeacher> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        boolean hasNext = pageTeacher.hasNext();//下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//上一页

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("items",records);
        hashMap.put("current",current);
        hashMap.put("pages",pages);
        hashMap.put("hasNext",hasNext);
        hashMap.put("hasPrevious",hasPrevious);
        return hashMap;
    }
}

package com.sx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.eduservice.entity.EduComment;
import com.sx.eduservice.entity.UcenterMember;
import com.sx.eduservice.fegin.UserfeginClient;
import com.sx.eduservice.mapper.EduCommentMapper;
import com.sx.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UserfeginClient userfeginClient;
    @Override
    public Map<String, Object> getCommentList(long page, long limit, String courseId) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId).orderByDesc("gmt_create");
        page(commentPage, queryWrapper);

        List<EduComment> commentList = commentPage.getRecords();
        long current = commentPage.getCurrent();//当前页
        long size = commentPage.getSize();//一页记录数
        long total = commentPage.getTotal();//总记录数
        long pages = commentPage.getPages();//总页数
        boolean hasPrevious = commentPage.hasPrevious();//是否有上页
        boolean hasNext = commentPage.hasNext();//是否有下页

        HashMap<String, Object> map = new HashMap<>();
        map.put("current",current);
        map.put("size",size);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        map.put("list",commentList);
        return map;
    }

    @Override
    public void save(EduComment eduComment, String id) {
        UcenterMember member = userfeginClient.getMemberInfoById(id);
        EduComment eduComment1 = new EduComment();
        BeanUtils.copyProperties(eduComment,eduComment1);
        eduComment1.setMemberId(member.getId());
        eduComment1.setNickname(member.getNickname());
        eduComment1.setAvatar(member.getAvatar());
        save(eduComment1);
    }
}

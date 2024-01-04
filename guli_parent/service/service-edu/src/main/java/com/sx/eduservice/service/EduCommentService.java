package com.sx.eduservice.service;

import com.sx.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getCommentList(long page, long limit, String courseId);

    void save(EduComment eduComment, String id);
}

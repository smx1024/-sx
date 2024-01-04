package com.sx.eduservice.service;

import com.sx.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sx.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author sx
 * @since 2023-11-02
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    boolean deleteCxhapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}

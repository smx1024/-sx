package com.sx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.eduservice.entity.EduChapter;
import com.sx.eduservice.entity.EduVideo;
import com.sx.eduservice.entity.vo.ChapterVo;
import com.sx.eduservice.entity.vo.VideoVo;
import com.sx.eduservice.mapper.EduChapterMapper;
import com.sx.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.eduservice.service.EduVideoService;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        //根据课程id查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> list = list(wrapper);
        //根据课程id查询课程里面的所有小节
        QueryWrapper<EduVideo> wrappervideo = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduVideo> videos = videoService.list(wrappervideo);
        //封装张杰list集合
        List<ChapterVo> chapterVos = list.stream().map(item -> {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(item.getId());
            chapterVo.setTitle(item.getTitle());
            //创建集合封装小节
            List<VideoVo> children = videos.stream().filter(video -> video.getChapterId().equals(item.getId()))
                    .map(video -> {
                        VideoVo videoVo = new VideoVo();
                        BeanUtils.copyProperties(video, videoVo);
                        return videoVo;
                    }).collect(Collectors.toList());
            chapterVo.setChildren(children);
            return chapterVo;
        }).collect(Collectors.toList());
        return chapterVos;
    }

    @Override
    public boolean deleteCxhapter(String chapterId) {
        //根据章节id查询小节表，如果查询到数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "不能删除");
        } else {
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        int delete = baseMapper.delete(wrapper);
        if (delete == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }
}

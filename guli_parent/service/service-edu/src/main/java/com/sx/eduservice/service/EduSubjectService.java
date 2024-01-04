package com.sx.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sx.eduservice.entity.EduSubject;
import com.sx.eduservice.entity.vo.SubjectTree;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目(EduSubject)表服务接口
 *
 * @author makejava
 * @since 2023-11-01 15:39:44
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<SubjectTree> getAllSubjectTree();
}


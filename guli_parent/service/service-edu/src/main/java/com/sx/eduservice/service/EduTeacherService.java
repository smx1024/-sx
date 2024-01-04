package com.sx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author sx
 * @since 2023-10-28
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> selectHotTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}

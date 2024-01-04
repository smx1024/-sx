package com.sx.eduservice.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.sx.eduservice.entity.EduSubject;

/**
 * 课程科目(EduSubject)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-01 15:41:07
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

/**
* 批量新增数据（MyBatis原生foreach方法）
*
* @param entities List<EduSubject> 实例对象列表
* @return 影响行数
*/
int insertBatch(@Param("entities") List<EduSubject> entities);

/**
* 批量新增或按主键更新数据（MyBatis原生foreach方法）
*
* @param entities List<EduSubject> 实例对象列表
* @return 影响行数
* @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
*/
int insertOrUpdateBatch(@Param("entities") List<EduSubject> entities);

}


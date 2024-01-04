package com.sx.eduservice.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfoVo {
    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程讲师id")
    private String teacherId;
    @ApiModelProperty(value = "课程专业父id")
    private String subjectParentId;
    @ApiModelProperty(value = "课程专业id")
    private String subjectId;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;
    @ApiModelProperty(value = "课程总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;


}

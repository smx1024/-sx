package com.sx.eduservice.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sx.eduservice.entity.vo.SubjectTree;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 课程科目(EduSubject)表实体类
 *
 * @author makejava
 * @since 2023-11-01 15:39:43
 */
@SuppressWarnings("serial")
@Data
@Accessors(chain = true)
public class EduSubject extends Model<EduSubject> {
    //课程类别ID
    private String id;
    @TableField(exist = false)
    private List<EduSubject> children;
    //类别名称
    private String title;
    //父ID
    private String parentId;
    //排序字段
    private String sort;
    //创建时间
    @TableField(fill= FieldFill.INSERT)
    private Date gmtCreate;
    //更新时间
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }


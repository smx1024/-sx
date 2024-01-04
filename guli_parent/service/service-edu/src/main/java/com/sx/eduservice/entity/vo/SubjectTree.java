package com.sx.eduservice.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SubjectTree {
    private String id;

    private String title;

    private  String parentId;

    private List<SubjectTree> children;
}

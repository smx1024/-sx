package com.sx.eduservice.controller;




import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduSubject;
import com.sx.eduservice.entity.vo.SubjectTree;
import com.sx.eduservice.service.EduSubjectService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 课程科目(EduSubject)表控制层
 *
 * @author makejava
 * @since 2023-11-01 15:39:43
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController{
    @Autowired
    private  EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public Result saveSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return Result.ok();
    }
    @GetMapping("/getAllSubject")
    public Result getAllSubjectTree(){
        List<SubjectTree> list = eduSubjectService.getAllSubjectTree();
        return  Result.ok().data("list",list);
    }

}


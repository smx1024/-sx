package com.sx.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.eduservice.entity.EduSubject;
import com.sx.eduservice.entity.excel.SubjectData;
import com.sx.eduservice.entity.vo.SubjectTree;
import com.sx.eduservice.listener.SubjectListener;
import com.sx.eduservice.mapper.EduSubjectMapper;
import com.sx.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程科目(EduSubject)表服务实现类
 *
 * @author makejava
 * @since 2023-11-01 15:39:44
 */
@Service("eduSubjectService")
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream stream = new FileInputStream((File) file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        EasyExcel.read((File) file, SubjectData.class, new SubjectListener(eduSubjectService)).sheet().doRead();
    }

    @Override
    public List<SubjectTree> getAllSubjectTree() {
        List<EduSubject> list = list(null);
        List<SubjectTree> treelist =new ArrayList<>();
        for (EduSubject a:list) {
            SubjectTree tree = new SubjectTree();
            BeanUtils.copyProperties(a,tree);
            treelist.add(tree);
            System.out.println(tree);
        }
        //封装为树形结构
        List<SubjectTree> tree = buildTree(treelist, "0");
        return tree;
    }

    private List<SubjectTree> buildTree(List<SubjectTree> list, String parentId) {
        List<SubjectTree> collect = list.stream().filter(item -> item.getParentId().equals(parentId)).map(item -> {
          item.setChildren( getChilldren(list,item.getId()));
          return item;
        }).collect(Collectors.toList());
        return collect;

    }

    private List<SubjectTree> getChilldren(List<SubjectTree> list,String id) {
        List<SubjectTree> collect = list.stream().filter(item -> item.getParentId().equals(id)).map(item -> {
          item.setChildren(getChilldren(list,item.getId()));
          return item;
        }).collect(Collectors.toList());
        return collect;
    }

}


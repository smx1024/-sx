package com.sx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.commonutils.Result;
import com.sx.eduservice.entity.EduTeacher;
import com.sx.eduservice.entity.vo.TeacherQuery;
import com.sx.eduservice.mapper.EduTeacherMapper;
import com.sx.eduservice.service.EduTeacherService;
import com.sx.serivcebase.handle.MyMetaObjectHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.poi.hssf.usermodel.HeaderFooter.page;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-10-28
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师表所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public Result getTeacherList() {
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result getTeacherById(@PathVariable("id") String id) {
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result removeById(@PathVariable("id") String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable long current,
                                  @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);

        //current 当前页
        //limit 每页记录数
        //把分页数据封装到pageTeacher对象里面
        //调用方法实现分页
        teacherService.page(page, null);
        return Result.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    //条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable long current,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //判断条件值是否为空，如果不为空拼接条件
        if (teacherQuery != null) {
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();
            if (name != null) {
                //构建条件
                wrapper.like("name", name);
            }
            if (level != null) {
                wrapper.eq("level", level);
            }
            if (begin != null) {
                wrapper.ge("gmt_create", begin);
            }
            if (end != null) {
                wrapper.le("gmt_create", end);
            }
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        teacherService.page(page, wrapper);
        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords();//数据list集合;
        return Result.ok().data("total", total).data("rows", records);
    }

    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        System.out.println(eduTeacher);
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}


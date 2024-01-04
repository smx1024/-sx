package com.sx.orderservice.fegin;

import com.sx.orderservice.entity.vo.EduCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
public interface CourseFegin {

    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public EduCourseVo getCourseInfoOrder(@PathVariable String id);
}

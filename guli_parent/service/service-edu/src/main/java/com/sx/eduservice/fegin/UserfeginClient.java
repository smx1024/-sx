package com.sx.eduservice.fegin;

import com.sx.commonutils.Result;
import com.sx.eduservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "service-ucenter")
public interface UserfeginClient {
    @GetMapping("/educenter/member/getMemberInfoById/{id}")
    public UcenterMember getMemberInfoById(@PathVariable("id") String id);
}

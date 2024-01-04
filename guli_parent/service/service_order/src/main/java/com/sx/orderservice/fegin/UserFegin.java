package com.sx.orderservice.fegin;

import com.sx.orderservice.entity.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
public interface UserFegin {
    @GetMapping("/educenter/member/getMemberInfoById/{id}")
    UcenterMember getMemberInfoById(@PathVariable("id") String id);
}

package com.sx.Userservice.service;

import com.sx.Userservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sx.Userservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author sx
 * @since 2023-11-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenId(String userId);

    Integer getCountRegister(String day);

}

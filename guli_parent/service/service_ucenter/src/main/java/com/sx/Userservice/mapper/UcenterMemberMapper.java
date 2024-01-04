package com.sx.Userservice.mapper;


import com.sx.Userservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2023-11-08
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getCountRegister(String day);
}

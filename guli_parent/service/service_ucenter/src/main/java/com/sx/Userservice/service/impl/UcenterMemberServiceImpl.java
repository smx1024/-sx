package com.sx.Userservice.service.impl;

import com.alibaba.nacos.client.config.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.Userservice.entity.UcenterMember;

import com.sx.Userservice.entity.vo.RegisterVo;
import com.sx.Userservice.mapper.UcenterMemberMapper;
import com.sx.Userservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.commonutils.JwtUtils;
import com.sx.commonutils.MD5Utils;
import com.sx.serivcebase.exception.GuliException;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-08
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(mobile==null||password==null){
            throw  new GuliException(20001,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember==null){
            throw  new GuliException(20001,"请先注册");
        }
        if( !MD5Utils.valid(password,ucenterMember.getPassword())){
            throw  new GuliException(20001,"密码错误");
        }
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        if(mobile==null||password==null||nickname==null||code==null){
            throw  new GuliException(20001,"注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw  new GuliException(20001,"验证码错误");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0){
            throw  new GuliException(20001,"手机号已经注册");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5Utils.MD5(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);
        member.setAvatar("https://guliedu-sx.oss-cn-beijing.aliyuncs.com/2023/11/03/%E6%97%A0%E6%A0%87%E9%A2%98.png");
        boolean save = save(member);
    }

    @Override
    public UcenterMember getByOpenId(String userId) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",userId);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }

    @Override
    public Integer getCountRegister(String day) {
        Integer i= baseMapper.getCountRegister(day);
        return  i;
    }
}

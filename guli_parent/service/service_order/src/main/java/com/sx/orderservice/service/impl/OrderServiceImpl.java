package com.sx.orderservice.service.impl;

import com.sx.orderservice.entity.Order;
import com.sx.orderservice.entity.vo.EduCourseVo;
import com.sx.orderservice.entity.vo.UcenterMember;
import com.sx.orderservice.fegin.CourseFegin;
import com.sx.orderservice.fegin.UserFegin;
import com.sx.orderservice.mapper.OrderMapper;
import com.sx.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.orderservice.utils.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UserFegin userFegin;
    @Autowired
    private CourseFegin courseFegin;
    @Override
    public String createOrder(String courseId, String id) {
        EduCourseVo courseVo = courseFegin.getCourseInfoOrder(courseId);
        UcenterMember member = userFegin.getMemberInfoById(id);
        Order order = new Order();
        order.setCourseCover(courseVo.getCover());
        order.setCourseId(courseId);
        order.setCourseTitle(courseVo.getTitle());
        order.setTotalFee(new BigDecimal(String.valueOf(courseVo.getPrice())));
        order.setTeacherName(courseVo.getTeacherName());
        order.setMemberId(id);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        String orderNo = OrderNoUtils.getOrderNo();

        order.setStatus(0);//未支付
        order.setPayType(1);//微信支付
        order.setOrderNo(orderNo);
        save(order);
        return   orderNo ;
    }
}

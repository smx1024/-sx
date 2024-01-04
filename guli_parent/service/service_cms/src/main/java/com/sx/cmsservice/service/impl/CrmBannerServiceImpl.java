package com.sx.cmsservice.service.impl;

import com.alibaba.nacos.api.cmdb.spi.CmdbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.sx.cmsservice.entity.CrmBanner;
import com.sx.cmsservice.mapper.CrmBannerMapper;
import com.sx.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.boot.Banner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-07
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public void pageBanner(long page, long limit) {

    }

    @Override
    @Cacheable(value = "banner",key="'selectIndexList'")
    public List<CrmBanner> getAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id").last("limit 2");
        List<CrmBanner> list = list(wrapper);
        return list;
    }
}

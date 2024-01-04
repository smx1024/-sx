package com.sx.cmsservice.service;

import com.sx.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author sx
 * @since 2023-11-07
 */
public interface CrmBannerService extends IService<CrmBanner> {

    void pageBanner(long page, long limit);

    List<CrmBanner> getAllBanner();

}

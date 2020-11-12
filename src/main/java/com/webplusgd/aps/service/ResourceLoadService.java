package com.webplusgd.aps.service;

import com.webplusgd.aps.exception.NoPlanException;
import com.webplusgd.aps.vo.ResourceLoadChart;

import java.util.Date;

public interface ResourceLoadService {
    /**
     * 获得开始日期后一周内的资源负载情况
     *
     * @param startDate 开始日期
     * @return 资源负载图
     */
    ResourceLoadChart getResourceLoadChart(Date startDate) throws NoPlanException;
}

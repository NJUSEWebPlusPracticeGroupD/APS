package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.service.ResourceLoadService;
import com.webplusgd.aps.vo.ResourceLoadChart;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Rollingegg
 * @create_time 11/9/2020 11:13 PM
 */
@Service("ResourceLoadService")
public class ResourceLoadServiceImpl implements ResourceLoadService {
    @Override
    public ResourceLoadChart getResourceLoadChart(Date startDate) {
        // TODO 返回资源负载图

        return new ResourceLoadChart();
    }
}

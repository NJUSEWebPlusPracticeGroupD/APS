package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.OrderResourceForm;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

@Component
public interface ProduceResourceFormService {
    ResponseVO<OrderResourceForm> getProduceResourceForm();
}

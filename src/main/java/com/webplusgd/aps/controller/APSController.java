package com.webplusgd.aps.controller;

import com.webplusgd.aps.model.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/aps")
public class APSController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/startAps", method = RequestMethod.GET)
    public ResponseVO startAps(@RequestParam Date currentDate){
        return null;
    }
}

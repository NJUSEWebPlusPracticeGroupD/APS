package com.webplusgd.aps.controller;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.vo.ResponseVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "APS", description = "The APS API")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/aps")
public class APSController {
    @Log("启动排程")
    @PostMapping("/startAps")
    public ResponseVO<Object> startAps(@RequestParam Date currentDate) {
        return null;
    }
}

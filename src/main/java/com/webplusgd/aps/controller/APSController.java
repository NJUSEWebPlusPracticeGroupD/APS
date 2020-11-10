package com.webplusgd.aps.controller;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.vo.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@Tag(name = "APS", description = "APS排程相关API")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/aps")
public class APSController {
    @Operation(summary = "启动排程")
    @Log("启动排程")
    @PostMapping("/startAps")
    public ResponseVO<Object> startAps(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "当前系统日期时间",example = "2018-09-07 12:00:00")
            @RequestParam Date currentDate) {
        return null;
    }
}

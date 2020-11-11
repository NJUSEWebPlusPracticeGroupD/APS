package com.webplusgd.aps.handler;

import com.webplusgd.aps.exception.NoPlanException;
import com.webplusgd.aps.exception.code.Code;
import com.webplusgd.aps.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Rollingegg
 * @create_time 11/11/2020 3:24 PM
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler({NoPlanException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO handleException(Exception e){
        log.error("系统内部异常，异常信息：", e);
        String mes=e instanceof NoPlanException?e.getMessage(): Code.C500.getDesc();
        return ResponseVO.buildFailure(mes);
    }
}

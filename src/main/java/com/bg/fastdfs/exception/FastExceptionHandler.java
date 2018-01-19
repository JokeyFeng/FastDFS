package com.bg.fastdfs.exception;

import com.bg.fastdfs.domain.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ketao on 2017/9/14.
 */
@ControllerAdvice
public class FastExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(FastExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultHandler(HttpServletRequest req, Exception ex) {
        log.error("global error:{}", ex.toString());
        String resultJson = new ResultJson(Boolean.FALSE, ex.getMessage(), ex.getMessage()).errorResult();
        ex.printStackTrace();
        return resultJson;
    }
}

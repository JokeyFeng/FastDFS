package com.bg.fastdfs.exception;

import com.bg.fastdfs.entity.Result;
import com.bg.fastdfs.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Chenjing
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultHandler(Exception e) {
        log.error("global error:{}", e);
        return ResultUtil.failed(e.getMessage());
    }
}

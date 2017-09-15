package com.bg.fastdfs.exception;

import com.bg.fastdfs.domain.FastException;
import com.bg.fastdfs.domain.ResponseError;
import com.bg.fastdfs.domain.ResultJson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ketao on 2017/9/14.
 */
@ControllerAdvice
public class FastExceptionHandler  {
    @ExceptionHandler
    @ResponseBody
    public  String  fastExceptionHandler(HttpServletRequest req, FastException ex){
        String resultJson= new ResultJson(Boolean.FALSE,ex.getData(), ex.getResponseError()).errorResult();
        ex.printStackTrace();
        return resultJson;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultHandler(HttpServletRequest req, Exception ex) throws Exception {
        String resultJson = new ResultJson(Boolean.FALSE,null, ResponseError.DEFAULT_EXCEPTION).errorResult();
        ex.printStackTrace();
        return resultJson;
    }
}

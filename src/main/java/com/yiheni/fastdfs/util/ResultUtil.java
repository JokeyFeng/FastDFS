package com.yiheni.fastdfs.util;

import com.yiheni.fastdfs.entity.HttpStatus;
import com.yiheni.fastdfs.entity.Result;

/**
 * Created by Chenjing on 2018/1/22.
 *
 * @author Chenjing
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result<Object> result = new Result<>();
        result.setSuccess(HttpStatus.SUCCESS.isStatus());
        result.setMessage(HttpStatus.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result failed(Object object) {
        Result<Object> result = new Result<>();
        result.setSuccess(HttpStatus.FAIL.isStatus());
        result.setMessage(HttpStatus.FAIL.getMsg());
        result.setData(object);
        return result;
    }
}

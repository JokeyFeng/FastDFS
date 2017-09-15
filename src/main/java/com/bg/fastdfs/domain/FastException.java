package com.bg.fastdfs.domain;

/**
 * Created by ketao on 2017/9/14.
 */
public class FastException extends Exception {

    private ResponseError responseError;

    private Object data;

    public FastException(ResponseError responseError,Object data) {
        super(responseError.getMessage());
        this.responseError = responseError;
        this.data=data;
    }

    public ResponseError getResponseError() {
        return responseError;
    }

    public void setResponseError(ResponseError responseError) {
        this.responseError = responseError;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

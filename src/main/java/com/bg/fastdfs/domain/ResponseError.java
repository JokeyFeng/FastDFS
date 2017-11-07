package com.bg.fastdfs.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ketao on 2017/6/10.
 */
public enum ResponseError {
    DEFAULT_EXCEPTION("-1","错误"),

    FIlE_UPLOAD_FAUILE("Fast_001","文件上传失败"),
    FIlE_DELETE_FAUILE("Fast_002","文件删除失败"),
    FIlE_URL_ISNULL("Fast_003","文件路径为空"),
    FIlE_DOWNLOAD_FAUILE("Fast_004","文件下载失败");

    private static Map<String, ResponseError> valueMap = new HashMap<String, ResponseError>();

    static {
        for(ResponseError exception : ResponseError.values()) {
            valueMap.put(exception.code, exception);
        }
    }
    private String code ;
    private String message ;

    ResponseError(String code , String message){
        this.code = code ;
        this.message = message ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

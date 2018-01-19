package com.bg.fastdfs.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by ketao on 2017/6/10.
 */
public class ResultJson {
    private Boolean isSuccess;

    private Object data;

    private String message;

    private Map<String,Object> map;

    public ResultJson() {

    }

    public ResultJson(Boolean isSuccess, Object data, String message) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.message = message;
    }

    public ResultJson(Boolean isSuccess, Object data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }
    public ResultJson(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String errorResult(){
        StringBuilder result=new StringBuilder();
        result.append("{");
        result.append("\"IsSuccess\":"+isSuccess+", ");
        result.append("\"Data\":"+data+", ");
        result.append("}");
        return result.toString();
    }

    public String successResult(){
        JSONObject json=new JSONObject();
        json.put("IsSuccess",isSuccess);
        json.put("Data",data);
        return json.toString();
    }




}

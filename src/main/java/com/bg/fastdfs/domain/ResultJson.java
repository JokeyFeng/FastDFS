package com.bg.fastdfs.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by ketao on 2017/6/10.
 */
public class ResultJson {
    private Boolean isSuccess;

    private Object data;

    private ResponseError responseError;

    private String message;

    private Map<String,Object> map;

    public ResultJson() {

    }

    public ResultJson(Boolean isSuccess, Object data, ResponseError responseError, Map<String,Object> map) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.responseError = responseError;
        this.map=map;
    }

    public ResultJson(Boolean isSuccess, Object data, ResponseError responseError) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.responseError = responseError;
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

    public ResponseError getResponseError() {
        return responseError;
    }

    public void setResponseError(ResponseError responseError) {
        this.responseError = responseError;
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
        result.append("\"ResponseError\":{\"Code\":\""+responseError.getCode()+"\",\"Message\":\""+responseError.getMessage()+"\"} ");
        result.append("}");
        return result.toString();
    }

    public String successResult(){
//        StringBuilder result=new StringBuilder();
        JSONObject json=new JSONObject();
        json.put("IsSuccess",isSuccess);
        json.put("Data",data);
       /* result.append("{");
        result.append("\"IsSuccess\":"+isSuccess+", ");
        result.append("\"Data\":"+json.getString("data"));
        result.append("}");*/
//        return result.toString();
        return json.toString();
    }

    //用于处理eds传过来的未打印数据
    public String backResultList_EDS(){
        JSONObject js=new JSONObject();
        JSONObject jData=new JSONObject();
        js.put("IsSuccess",isSuccess);
        jData.put("EDS_OrderSimple",data);
        if(!map.isEmpty()){
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                  Map.Entry entry = (Map.Entry) iter.next();
                  String key = (String)entry.getKey();
                  Object val = entry.getValue();
                jData.put(key,val);
            }
        }
        js.put("Data",jData);
        return js.toString();
    }


}

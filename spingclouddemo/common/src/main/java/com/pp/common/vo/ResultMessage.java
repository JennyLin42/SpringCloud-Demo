package com.pp.common.vo;

public class ResultMessage {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应结果
     */
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    public ResultMessage(){

    }

    public ResultMessage(String code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
}

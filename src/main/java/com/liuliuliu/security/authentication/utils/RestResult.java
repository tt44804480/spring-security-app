package com.liuliuliu.security.authentication.utils;

import lombok.ToString;

/**
 * 数据返回格式
 */
@ToString
public class RestResult<t> {

    //状态码
    private int code;

    //提示消息
    private String msg;

    //来源
    private String since;

    //数据
    private t data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public t getData() {
        return data;
    }

    public void setData(t data) {
        this.data = data;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}

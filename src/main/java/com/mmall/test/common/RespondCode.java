package com.mmall.test.common;

public enum  RespondCode {
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");


    private final  int code;
    private final  String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    RespondCode(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}

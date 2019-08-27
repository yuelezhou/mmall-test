package com.mmall.test.common;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int status;
    private T data;
    private String message;

    @JsonIgnore
    public boolean isSuccess(){
        return this.getStatus() == RespondCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new  ServerResponse<T>(RespondCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(T data){
        return new  ServerResponse<T>(RespondCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String message){
        return new  ServerResponse<T>(RespondCode.SUCCESS.getCode(),message);
    }

    public static <T> ServerResponse<T> createBySuccess(T data ,String message){
        return new  ServerResponse<T>(RespondCode.SUCCESS.getCode(),data,message);
    }


    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(RespondCode.ERROR.getCode(),RespondCode.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String message){
        return new ServerResponse<T>(RespondCode.ERROR.getCode(),message);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int code,String message){
        return new ServerResponse<T>(code,message);
    }




    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServerResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ServerResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}

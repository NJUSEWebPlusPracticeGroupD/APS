package com.webplusgd.aps.model;

public class ResponseVO {
    private boolean success;
    private String message;
    private Object content;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }


    public static ResponseVO buildSuccess() {
        ResponseVO response = new ResponseVO();
        response.setSuccess(true);
        return response;
    }

    public static ResponseVO buildSuccess(Object content) {
        ResponseVO response = new ResponseVO();
        response.setContent(content);
        response.setSuccess(true);
        return response;
    }

    public static ResponseVO buildFailure(String message) {
        ResponseVO response = new ResponseVO();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}

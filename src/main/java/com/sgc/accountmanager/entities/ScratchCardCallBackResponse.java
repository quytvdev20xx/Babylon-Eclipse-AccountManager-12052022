package com.sgc.accountmanager.entities;

public class ScratchCardCallBackResponse {
    private String userId;
    private int value;
    private String response;
    private String token;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PayCardCallBackResponse{" +
                "userId='" + userId + '\'' +
                ", value='" + value + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
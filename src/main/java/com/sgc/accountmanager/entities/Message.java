package com.sgc.accountmanager.entities;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class Message implements Serializable{

    private int status;
    private String message;
    private Customer data  = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Customer getData() {
		return data;
	}

	public void setData(Customer data) {
		this.data = data;
	}

//    public JsonNode getData() {
//        return data;
//    }
//
//    public void setData(JsonNode data) {
//        this.data = data;
//    }

}
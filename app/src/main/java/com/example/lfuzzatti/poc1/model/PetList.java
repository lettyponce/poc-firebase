package com.example.lfuzzatti.poc1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PetList {

    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("pets")
    @Expose
    private ArrayList<Pet> pets;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setData(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "PetList{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", pets=" + pets +
                '}';
    }
}

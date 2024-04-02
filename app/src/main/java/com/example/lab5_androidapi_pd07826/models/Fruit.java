package com.example.lab5_androidapi_pd07826.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Fruit {
    private String name, quantity, price, status, desciption, createdAt, updatedAt;
    private ArrayList<String> imageList;
    @SerializedName("id_distributor")
    private String distributor;

    public Fruit() {
    }

    public Fruit(String name, String quantity, String price, String status, String desciption, String createdAt, String updatedAt, ArrayList<String> imageList, String distributor) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.desciption = desciption;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageList = imageList;
        this.distributor = distributor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }
}

package com.yourstronghelper.grzegorzmacko.yourstronghelper.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private String type;
    private int series, quantity;
    private String idd;
    @Exclude
    private String id;

    public Exercise(){

    }

    public Exercise(String idd, String name, String type, int series, int quantity) {
        this.idd = idd;
        this.name = name;
        this.type = type;
        this.series = series;
        this.quantity = quantity;
    }

    public Exercise( String name, String type, int series, int quantity) {
        this.name = name;
        this.type = type;
        this.series = series;
        this.quantity = quantity;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

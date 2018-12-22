package com.yourstronghelper.grzegorzmacko.yourstronghelper.model;

public class Exercise {
    private String name;
    private String type;
    private int series, quantity;

    public Exercise(){

    }

    public Exercise(String name, String type, int series, int quantity) {
        this.name = name;
        this.type = type;
        this.series = series;
        this.quantity = quantity;
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

package com.yourstronghelper.grzegorzmacko.yourstronghelper.model;

public class exercise {
    private String name;
    private char type;
    private int series, quantity;

    public exercise(){

    }

    public exercise(String name, char type, int series, int quantity) {
        this.name = name;
        this.type = type;
        this.series = series;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public char getType() {
        return type;
    }

    public int getSeries(){
        return series;
    }
    public int getQuantity(){
        return quantity;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package com.yourstronghelper.grzegorzmacko.yourstronghelper.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class TrainingPlan implements Serializable {
    private String idd;
    private String name, plan;
    @Exclude
    private String id;

    public TrainingPlan(String idd, String name, String plan) {
        this.id = idd;
        this.name = name;
        this.plan = plan;
    }

    public TrainingPlan(){}

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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}

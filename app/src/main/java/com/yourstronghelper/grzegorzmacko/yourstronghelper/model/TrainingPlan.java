package com.yourstronghelper.grzegorzmacko.yourstronghelper.model;

import java.io.Serializable;

public class TrainingPlan implements Serializable {
    private String id;
    private String name, plan;

    public TrainingPlan(String id, String name, String plan) {
        this.id = id;
        this.name = name;
        this.plan = plan;
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

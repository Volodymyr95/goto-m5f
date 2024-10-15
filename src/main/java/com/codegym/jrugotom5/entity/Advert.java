package com.codegym.jrugotom5.entity;

import java.util.Date;

public class Advert {
    private Long id;
    private String name;
    private String description;
    private User createdBy;
    private Date createdDate;
    private Date endDate;
    private Boolean isActive;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public User getCreatedBy() {
        return createdBy;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public Boolean getIsActive() {
        return isActive;
    }
}

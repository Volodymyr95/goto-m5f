package com.codegym.jrugotom5.dto;

import com.codegym.jrugotom5.entity.User;

import java.util.Date;

public class AdvertDTO {
    private Long id;
    private String name;
    private String description;
    private User createdBy;
    private Date createdDate;
    private Date endDate;
    private Boolean isActive;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}

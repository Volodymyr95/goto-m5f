package com.codegym.jrugotom5.dto;

import com.codegym.jrugotom5.entity.User;

import java.time.LocalDate;

public class AdvertDTO {
    private Long id;
    private String title;
    private String description;
    private User createdBy;
    private LocalDate createdDate;
    private LocalDate endDate;
    private Boolean isActive;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}

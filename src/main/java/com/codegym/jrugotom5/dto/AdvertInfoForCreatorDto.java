package com.codegym.jrugotom5.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdvertInfoForCreatorDto {
    private Long id;
    private String title;
    private LocalDate createdDate;
    private LocalDate endDate;
    private Boolean isActive;
}

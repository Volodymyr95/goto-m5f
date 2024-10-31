package com.codegym.jrugotom5.dto;

import lombok.Data;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class AdvertInfoForCreatorDto {
    @Min(value = 1, message = "Id must be greater than or equals to 1")
    private Long id;
    private String title;
    private LocalDate createdDate;
    private LocalDate endDate;
    private Boolean isActive;
}

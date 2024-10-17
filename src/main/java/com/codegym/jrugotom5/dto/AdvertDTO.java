package com.codegym.jrugotom5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AdvertDTO {
    private Long id;
    private String title;
    private String description;
    private Long userCreatorId;
    private LocalDate createdDate;
    private LocalDate endDate;
    private Boolean isActive;
}

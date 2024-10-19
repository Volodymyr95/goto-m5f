package com.codegym.jrugotom5.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AdvertDTO {
    private Long id;
    private String title;
    private String description;
    private Long userCreatorId;
    private LocalDate createdDate;
    private LocalDate endDate;
}

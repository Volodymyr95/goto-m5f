package com.codegym.jrugotom5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
public class AdvertFullInfoDTO {
    private Long id;
    private String title;
    private String description;
    private Long userCreatorId;
    private LocalDate createdDate;
    private LocalDate endDate;
    private Boolean isActive;
}

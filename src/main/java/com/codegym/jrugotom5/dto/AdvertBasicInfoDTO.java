package com.codegym.jrugotom5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AdvertBasicInfoDTO {
    private Long id;
    private String title;
    private LocalDate createdDate;
    private Boolean isActive;
}

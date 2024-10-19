package com.codegym.jrugotom5.dto;

import com.codegym.jrugotom5.entity.User;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AdvertDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private LocalDate createdDate;
    private LocalDate endDate;
}

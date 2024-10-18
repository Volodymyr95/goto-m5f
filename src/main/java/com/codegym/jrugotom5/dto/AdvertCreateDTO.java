package com.codegym.jrugotom5.dto;

import com.codegym.jrugotom5.entity.User;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Getter
@Setter
public class AdvertCreateDTO {

    @NotNull(message = "Title is required")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

    @NotNull
    private User createdBy;

    private LocalDate createdDate;

    private LocalDate endDate;

    private Boolean isActive;
}

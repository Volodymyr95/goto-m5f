package com.codegym.jrugotom5.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AdvertDTO {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 20, max = 100, message = "Title must be between 20 and 100 characters")
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 20, max = 100, message = "Description must be between 20 and 100 characters")
    private String description;

    @NotNull(message = "User ID cannot be null")
    private Long userId;;

    private LocalDate createdDate;
    private LocalDate endDate;

    public void setCreatorId(Long userId) {
    }
}

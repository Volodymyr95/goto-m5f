package com.codegym.jrugotom5.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AdvertUpdateDTO extends AdvertDTO{
    @Min(value = 1,message = "Id cannot be less than value=1")
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
}

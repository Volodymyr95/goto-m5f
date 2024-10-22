package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.entity.Category;
import com.codegym.jrugotom5.exception.InvalidCategoryException;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/adverts")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;

    @GetMapping()
    public List<AdvertBasicInfoDTO> getAllAdverts() {
        return advertService.getAllAdverts();
    }

    @GetMapping(path = "/date")
    public List<AdvertFullInfoDTO> getAdverts(@RequestParam LocalDate from, LocalDate to) {
        return advertService.getAdvertsByDateRange(from, to);
    }

    @GetMapping("/{category}/")
    public List<AdvertBasicInfoDTO> getByCategory(@PathVariable String category) {
        try {
            Category enumCategory = Category.valueOf(category.toUpperCase());
            return advertService.getByCategory(enumCategory);
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException("Invalid category: %s".formatted(category));
        }
    }
}
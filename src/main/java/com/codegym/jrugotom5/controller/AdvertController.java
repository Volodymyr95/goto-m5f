package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.service.AdvertService;
import jakarta.validation.Valid;
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
    public List<AdvertFullInfoDTO> getByDateRange(@RequestParam LocalDate from, LocalDate to) {
        return advertService.getAdvertsByDateRange(from, to);
    }
    @PutMapping("/update")
    public AdvertDTO update(@Valid @RequestBody AdvertDTO advertDTO) {

        return advertService.update(advertDTO);
    }

    @GetMapping("/adverts")
    public List<AdvertBasicInfoDTO> getByTitleContains(@RequestParam(value = "title") String phrase) {
        return advertService.getByTitleContains(phrase);
    }

    @GetMapping("/{category}/")
    public List<AdvertBasicInfoDTO> getByCategory(@PathVariable String category) {
        return advertService.getByCategory(category);

    }
}


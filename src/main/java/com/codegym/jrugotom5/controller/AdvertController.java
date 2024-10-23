package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/adverts")
    public List<AdvertBasicInfoDTO> getByTitleContains(@RequestParam(value = "title") String phrase) {
        return advertService.getByTitleContains(phrase);
    }
}
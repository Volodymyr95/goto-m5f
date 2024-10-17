package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/advert")
@RequiredArgsConstructor
public class AdvertController {
   private final AdvertService advertService;

    @GetMapping(path = "/", produces = "application/json")
    public List<AdvertDTO> getAdverts(@RequestParam LocalDate from, LocalDate to) {

        return advertService.getAdvertsByDateRange(from, to);
    }
}


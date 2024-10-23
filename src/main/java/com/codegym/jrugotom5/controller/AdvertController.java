package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertCreateDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.service.AdvertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<AdvertFullInfoDTO> createAdvert(@RequestBody @Valid AdvertCreateDTO advertCreateDTO) {
        AdvertFullInfoDTO advertFullInfoDTO = advertService.createAdvert(advertCreateDTO);
        return new ResponseEntity<>(advertFullInfoDTO, HttpStatus.CREATED);
    }
}
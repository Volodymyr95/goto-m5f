package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.dto.AdvertUpdateDTO;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/advert")
@RequiredArgsConstructor
public class AdvertController {
   private final AdvertService advertService;

    @GetMapping(path = "/", produces = "application/json")
    public List<AdvertDTO> getAdverts(@RequestParam LocalDate from, LocalDate to) {

        return advertService.getAdvertsByDateRange(from, to);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AdvertUpdateDTO> update(@PathVariable Long id, @RequestBody @Validated AdvertUpdateDTO advertUpdateDTO) {
        AdvertUpdateDTO updatedAdvert = advertService.update(id, advertUpdateDTO);
        return ResponseEntity.ok(updatedAdvert);
    }
}


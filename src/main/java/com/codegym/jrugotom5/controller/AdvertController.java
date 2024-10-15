package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {
   private final AdvertService advertService;

    @GetMapping(path = "/", produces = "application/json")
    public List<AdvertDTO> getAdverts(@RequestParam Date from, Date to) {

        List<Advert> adverts = advertService.getAdvertsByDateRange(from, to);
        return adverts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AdvertDTO convertToDTO(Advert advert) {
        AdvertDTO dto = new AdvertDTO();
        dto.setId(advert.getId());
        dto.setName(advert.getName());
        dto.setDescription(advert.getDescription());
        dto.setCreatedDate(advert.getCreatedDate());
        dto.setEndDate(advert.getEndDate());
        dto.setCreatedBy(advert.getCreatedBy());
        dto.setIsActive(advert.getIsActive());
        return dto;
    }

}


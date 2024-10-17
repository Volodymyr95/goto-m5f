package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ads")
public class AdvertController {
    private final AdvertService advertService;

    @GetMapping("/{userId}")
    public List<AdvertInfoForCreatorDto> getAdvertsByUser(@PathVariable Long userId) {
        return advertService.getAdvertsByCreateBy(userId);
    }
}

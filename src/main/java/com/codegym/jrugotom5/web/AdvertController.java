package com.codegym.jrugotom5.web;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/adverts")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;

    @GetMapping
    public List<Advert> getByCategory(@RequestParam String category) {
        return advertService.getByCategory(category);
    }
}

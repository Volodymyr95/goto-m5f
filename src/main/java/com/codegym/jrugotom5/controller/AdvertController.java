package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ads")
public class AdvertController {
    private final AdvertService advertService;

    @GetMapping("/{userId}")
    public List<Advert> getAdvertsByUser (@PathVariable int userId) {
        return advertService.getAdvertsByUser(userId);
    }
}

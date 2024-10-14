package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/adverts")
public class AdvertController {
    public AdvertService advertService;

    public AdvertController(@Autowired AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<Advert> getAdverts(@RequestParam Date from, Date to) {
        return this.advertService.getAdvertsByDateRange(from, to);
    }

}


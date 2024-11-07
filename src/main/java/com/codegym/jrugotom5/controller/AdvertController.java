package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/adverts")
    public List<AdvertBasicInfoDTO> getByTitleContains(@RequestParam(value = "title") String phrase) {
        return advertService.getByTitleContains(phrase);
    }

    @GetMapping("/{category}/")
    public List<AdvertBasicInfoDTO> getByCategory(@PathVariable String category) {
        return advertService.getByCategory(category);

    }

    @DeleteMapping("/{id}")
    public void deleteAdvert(@PathVariable Long id) {
        advertService.deleteAdvertById(id);
    }

    @DeleteMapping("/title/{title}")
    public void deleteAdvertByTtile(@PathVariable String title) {
        advertService.deleteAdvertByTitle(title);
    }


    @DeleteMapping("/user/{userId}")
    public void deleteAdvertsByUserId(@PathVariable Long userId) {
        advertService.deleteAdvertsByUserId(userId);
    }

    @DeleteMapping("/description")
    public void deleteAdvertsByDescription(@RequestParam String description) {
        advertService.deleteAdvertsByDescriptionLike(description);
    }

}
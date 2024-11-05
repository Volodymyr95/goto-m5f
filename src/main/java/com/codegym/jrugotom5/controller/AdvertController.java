package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertCreateDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.service.AdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

    @Operation(summary = "Create new advert", tags = {"advert"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Advert created", content = {@Content(schema = @Schema)}),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = {@Content(schema = @Schema)}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema)})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertFullInfoDTO createAdvert(@RequestBody @Validated AdvertCreateDTO advertCreateDTO) {
        return advertService.createAdvert(advertCreateDTO);
    }
}
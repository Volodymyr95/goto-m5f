package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.service.AdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Advert Controller", description = "Advert APIs")
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

    @Operation(
            summary = "Retrieve an Advert by Id",
            description = """
                     Get an Advert object by specifying its id.\s
                     The response is Advert object with id, title, description, user creator id, created date,\s
                     end date and active status.
                    \s""",
            tags = {"adverts", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AdvertFullInfoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}, description = "Receive a 400 error if id is less than 1"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}, description = "Receive a 404 error if the Advert is not found by this id")
    })
    @GetMapping("/{id}")
    public AdvertFullInfoDTO getAdvertById(@PathVariable @Min(value = 1,
            message = "Advert id must be greater than 0") Long id) {
        return advertService.getAdvertById(id);
    }
}
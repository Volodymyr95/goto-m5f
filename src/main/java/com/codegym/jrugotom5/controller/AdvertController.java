package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.service.AdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Advertisement Controller", description = "Advertisement APIs")
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

    @Operation(
            summary = "Retrieve all adverts created by a specific user",
            description = "Get a list of adverts created by a user by specifying the user's id.",
            tags = {"adverts", "user", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AdvertInfoForCreatorDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}, description = "400 error occurs if user's id is less than 1"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}, description = "404 error occurs if there is no user with the specified id")
    })
    @GetMapping("/user")
    public List<AdvertInfoForCreatorDto> getAdvertsByUser(@RequestParam(value = "id") @Min(value = 1, message = "Id must be greater than or equals to 1") Long userId) {
        return advertService.getAdvertsByUserId(userId);
    }

    @GetMapping("/adverts")
    public List<AdvertBasicInfoDTO> getByTitleContains(@RequestParam(value = "title") String phrase) {
        return advertService.getByTitleContains(phrase);
    }

    @GetMapping("/{category}/")
    public List<AdvertBasicInfoDTO> getByCategory(@PathVariable String category) {
        return advertService.getByCategory(category);

    }
}
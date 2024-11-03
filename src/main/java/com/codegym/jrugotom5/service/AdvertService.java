package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import com.codegym.jrugotom5.exception.InvalidCategoryException;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ModelMapper modelMapper;

    public List<AdvertFullInfoDTO> getAdvertsByDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to) || from.isEqual(to)) {
            throw new InvalidDateRangeException("'From' date should be after 'To' date.");
        }
        List<Advert> adverts = advertRepository.findAllByCreatedDateBetween(from, to);

        return adverts.stream()
                .map(advert -> {
                    AdvertFullInfoDTO dto = modelMapper.map(advert, AdvertFullInfoDTO.class);
                    dto.setUserCreatorId(advert.getCreatedBy().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AdvertBasicInfoDTO> getAllAdverts() {
        return Streamable.of(advertRepository.findAll())
                .map(advert -> modelMapper.map(advert, AdvertBasicInfoDTO.class))
                .toList();
    }

    public List<AdvertBasicInfoDTO> getByTitleContains(String phrase) {
        return advertRepository.findAllByTitleContainsIgnoreCase(phrase)
                .stream()
                .map(advert -> modelMapper.map(advert, AdvertBasicInfoDTO.class))
                .toList();
    }

    public List<AdvertBasicInfoDTO> getByCategory(String category) {
        try {
            Category enumCategory = Category.valueOf(category.toUpperCase());
            return advertRepository.findAllByCategory(enumCategory)
                    .stream()
                    .map(advert -> modelMapper.map(advert, AdvertBasicInfoDTO.class))
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException("Invalid category: %s".formatted(category));
        }

    }

    public void deleteAdvertById(Long id) {
        if (!advertRepository.existsById(id)) {
            log.error("Advert with ID {} not found", id);
            throw new EntityNotFoundException("Advert with %d ID not found".formatted(id));
        }
        advertRepository.deleteById(id);
    }

    public void deleteAdvertByTitle(String title) {
        if (!advertRepository.existsByTitle(title)) {
            log.error("Advert with title {} not found", title);
            throw new EntityNotFoundException("Advert with title %s not found".formatted(title));
        }
        advertRepository.deleteByTitle(title);
    }

    public void deleteAdvertsByUserId(Long userId) {
        if (!advertRepository.existsByCreatedBy_Id(userId)) {
            log.error("No adverts found for user with ID {}", userId);
            throw new EntityNotFoundException("No adverts found for user with ID " + userId);
        }
        advertRepository.deleteByCreatedBy_Id(userId);
    }

    public void deleteAdvertsByDescriptionLike(String description) {
        List<Advert> advertsToDelete = advertRepository.findByDescriptionContaining(description);
        if (advertsToDelete.isEmpty()) {
            log.error("No adverts found with description like {}", description);
            throw new EntityNotFoundException("No adverts found with description like " + description);
        }
        advertRepository.deleteByDescriptionContaining(description);
    }

}

package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import com.codegym.jrugotom5.exception.InvalidAdDelException;
import com.codegym.jrugotom5.exception.InvalidCategoryException;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.InvalidUserIdException;
import com.codegym.jrugotom5.repository.AdvertRepository;
import jakarta.transaction.Transactional;
import com.codegym.jrugotom5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

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

    public List<AdvertInfoForCreatorDto> getAdvertsByUserId(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserIdException("There is no user with this id %d".formatted(id)));
        return advertRepository.getAdvertsByCreatedById(id).stream()
                .map(advert -> modelMapper.map(advert, AdvertInfoForCreatorDto.class))
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

    @Transactional
    public void deleteAdvertById(Long id) {
        if (!advertRepository.existsById(id)) {
            throw new InvalidAdDelException("Advert with %d ID not found".formatted(id));
        }
        advertRepository.deleteById(id);
    }

    @Transactional
    public void deleteAdvertsByUserId(Long userId) {
        if (!advertRepository.existsByCreatedById(userId)) {
            throw new InvalidAdDelException("No adverts found for user with ID " + userId);
        }
        advertRepository.deleteByCreatedById(userId);
    }

}

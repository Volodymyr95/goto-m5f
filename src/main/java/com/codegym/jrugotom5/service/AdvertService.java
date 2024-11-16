package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.exception.InvalidCategoryException;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.InvalidIdException;
import com.codegym.jrugotom5.repository.AdvertRepository;
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
    public AdvertDTO update(AdvertDTO advertDTO) {
        Advert advert = advertRepository.findById(advertDTO.getId())
                .orElseThrow(() -> new InvalidIdException("Advert not found with ID: " ));

        Long userId = advertDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " ));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(advertDTO, advert);
        advert.setCreatedBy(user);

        AdvertDTO dto = modelMapper.map(advertRepository.save(advert), AdvertDTO.class);
        dto.setCreatorId(userId);
        return dto;
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
}
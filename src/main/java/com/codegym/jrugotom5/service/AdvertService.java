package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.InvalidUserIdException;
import com.codegym.jrugotom5.repository.AdvertRepository;
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

    public List<AdvertFullInfoDTO> getAdvertsByDateRange(LocalDate from, LocalDate to) {

        if (from.isAfter(to) || from.isEqual(to)) {
            throw new InvalidDateRangeException("'From' date should be after 'To' date.");
        }
        List<Advert> adverts = this.advertRepository.findAllByCreatedDateBetween(from, to);

        return adverts.stream()
                .map(advert ->
                {
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

    public List<AdvertInfoForCreatorDto> getAdvertsByUserId(Long id) {
        if (id < 1) {
            throw new InvalidUserIdException("User id " + id + " is invalid. Id must be greater than 0");
        }
        return advertRepository.getAdvertsByCreatedById(id).stream()
                .map(advert -> modelMapper.map(advert, AdvertInfoForCreatorDto.class))
                .toList();
    }
}

package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
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

    public List<AdvertDTO> getAdvertsByDateRange(LocalDate from, LocalDate to) {

        if (from.isAfter(to) || from.isEqual(to)) {
            throw new InvalidDateRangeException("'From' date should be after 'To' date.");
        }
        List<Advert> adverts = this.advertRepository.findAllByCreatedDateBetweenFromTo(from, to);

        return adverts.stream()
                .map(advert -> modelMapper.map(advert, AdvertDTO.class))
                .collect(Collectors.toList());
    }

    public List<AdvertBasicInfoDTO> getAllAdverts() {
        return Streamable.of(advertRepository.findAll())
                .map(advert->modelMapper.map(advert, AdvertBasicInfoDTO.class))
                .toList();
    }
    private final AdvertRepository advertRepository;

    public List<Advert> getByCategory(String category) {
        return advertRepository.findByCategory(category);
    }
}

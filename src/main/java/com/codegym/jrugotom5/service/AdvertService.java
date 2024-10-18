package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
}

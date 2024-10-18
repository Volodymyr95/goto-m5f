package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ModelMapper modelMapper;

    public List<AdvertBasicInfoDTO> getAllAdverts() {
        List<Advert> allAdverts = (List<Advert>) advertRepository.findAll();
        return allAdverts.stream()
                .map(advert->modelMapper.map(advert, AdvertBasicInfoDTO.class))
                .toList();
    }
}

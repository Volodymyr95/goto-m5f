package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ModelMapper mapper;

    public List<AdvertInfoForCreatorDto> getAdvertsByCreateBy(Long id) {
        try{
            List<Advert> adverts = advertRepository.getAdvertsByCreatedBy_Id(id);
            if(adverts.isEmpty()){
                log.info("No adverts found for id {}", id);
                return Collections.emptyList();
            }
            return adverts.stream()
                    .map(advert -> mapper.map(advert, AdvertInfoForCreatorDto.class))
                    .toList();
        }catch (Exception e){
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}

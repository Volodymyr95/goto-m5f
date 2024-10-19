package com.codegym.jrugotom5.converter;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class AdvertToDtoConverter implements Converter<Advert, AdvertDTO> {
    @Override
    public AdvertDTO convert(MappingContext<Advert, AdvertDTO> context) {
        Advert advert = context.getSource();
        return AdvertDTO.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .createdDate(advert.getCreatedDate())
                .endDate(advert.getEndDate())
                .userCreatorId(advert.getCreatedBy().getId())
                .isActive(advert.getIsActive())
                .build();
    }
}
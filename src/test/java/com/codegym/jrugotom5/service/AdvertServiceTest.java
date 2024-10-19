package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdvertServiceTest {
    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AdvertService advertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAdvertsByCreateBy_WithAdverts_ReturnsDtoList() {
        Long userId = 1L;
        Advert advert1 = new Advert();
        Advert advert2 = new Advert();
        List<Advert> adverts = List.of(advert1, advert2);

        when(advertRepository.getAdvertsByCreatedBy_Id(userId)).thenReturn(adverts);

        AdvertInfoForCreatorDto dto1 = new AdvertInfoForCreatorDto();
        AdvertInfoForCreatorDto dto2 = new AdvertInfoForCreatorDto();

        when(modelMapper.map(advert1, AdvertInfoForCreatorDto.class)).thenReturn(dto1);
        when(modelMapper.map(advert2, AdvertInfoForCreatorDto.class)).thenReturn(dto2);

        List<AdvertInfoForCreatorDto> result = advertService.getAdvertsByCreateBy(userId);

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(advertRepository).getAdvertsByCreatedBy_Id(userId);
    }

    @Test
    void testGetAdvertsByCreateBy_NoAdverts_ReturnsEmptyList() {
        Long userId = 1L;
        when(advertRepository.getAdvertsByCreatedBy_Id(userId)).thenReturn(Collections.emptyList());

        List<AdvertInfoForCreatorDto> result = advertService.getAdvertsByCreateBy(userId);

        assertTrue(result.isEmpty());
        verify(advertRepository).getAdvertsByCreatedBy_Id(userId);
    }

}
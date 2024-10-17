package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    void testGetAdvertsByDateRange() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        Advert advert1 = new Advert();
        advert1.setId(1L);
        advert1.setTitle("Advert 1");

        Advert advert2 = new Advert();
        advert2.setId(2L);
        advert2.setTitle("Advert 2");

        List<Advert> adverts = Arrays.asList(advert1, advert2);

        AdvertDTO advertDTO1 = new AdvertDTO();
        advertDTO1.setId(1L);
        advertDTO1.setTitle("Advert 1");

        AdvertDTO advertDTO2 = new AdvertDTO();
        advertDTO2.setId(2L);
        advertDTO2.setTitle("Advert 2");

        when(advertRepository.findAllByCreatedDateBetweenFromTo(from, to)).thenReturn(adverts);
        when(modelMapper.map(advert1, AdvertDTO.class)).thenReturn(advertDTO1);
        when(modelMapper.map(advert2, AdvertDTO.class)).thenReturn(advertDTO2);

        List<AdvertDTO> result = advertService.getAdvertsByDateRange(from, to);

        assertEquals(2, result.size());
        assertEquals("Advert 1", result.get(0).getTitle());
        assertEquals("Advert 2", result.get(1).getTitle());
    }
}

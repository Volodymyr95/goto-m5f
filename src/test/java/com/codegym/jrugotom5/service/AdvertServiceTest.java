package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.dto.AdvertInfoForCreatorDto;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdvertServiceTest {
    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private ModelMapper modelMapper;

    private AdvertService advertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        advertService = new AdvertService(advertRepository, modelMapper);
    }

    @Test
    public void testGetAllAdverts_OneAdvert() {
        AdvertBasicInfoDTO expectedDto = new AdvertBasicInfoDTO();
        expectedDto.setId(1L);
        expectedDto.setTitle("Test title");
        expectedDto.setCreatedDate(LocalDate.of(2024, 1, 1));
        expectedDto.setIsActive(true);

        when(advertRepository.findAll()).thenReturn(List.of(new Advert()));
        when(modelMapper.map(any(Advert.class), eq(AdvertBasicInfoDTO.class))).thenReturn(expectedDto);

        List<AdvertBasicInfoDTO> dtoListFromService = advertService.getAllAdverts();

        assertEquals(List.of(expectedDto), dtoListFromService);

        verify(advertRepository).findAll();
    }

    @Test
    void testGetAdvertsByDateRange() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        int numberOfAdverts = 2;
        List<Advert> adverts = new ArrayList<>(numberOfAdverts);
        List<AdvertFullInfoDTO> expectedDtoList = new ArrayList<>(numberOfAdverts);

        when(advertRepository.findAllByCreatedDateBetween(from, to)).thenReturn(adverts);

        for (int i = 0; i < numberOfAdverts; i++) {
            String advertTitle = "Advert " + i;

            Advert advert = new Advert();
            advert.setId((long) i);
            advert.setTitle(advertTitle);
            adverts.add(advert);

            AdvertFullInfoDTO advertFullInfoDTO = new AdvertFullInfoDTO();
            advertFullInfoDTO.setId((long) i);
            advertFullInfoDTO.setTitle(advertTitle);
            expectedDtoList.add(advertFullInfoDTO);

            when(modelMapper.map(advert, AdvertFullInfoDTO.class)).thenReturn(advertFullInfoDTO);
        }
        List<AdvertFullInfoDTO> dtoListFromService = advertService.getAdvertsByDateRange(from, to);
        verify(advertRepository).findAllByCreatedDateBetween(from, to);

        String assertMessage = "The adverts list should match the expected list in both order and content";
        assertIterableEquals(expectedDtoList, dtoListFromService, assertMessage);
    }

    @Test
    void testGetAdvertsByDateRange_InvalidDateRange() {
        LocalDate from = LocalDate.of(2023, 12, 31);
        LocalDate to = LocalDate.of(2023, 1, 1);

        InvalidDateRangeException exception = assertThrows(InvalidDateRangeException.class, () -> {
            advertService.getAdvertsByDateRange(from, to);
        });

        assertEquals("'From' date should be after 'To' date.", exception.getMessage());
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

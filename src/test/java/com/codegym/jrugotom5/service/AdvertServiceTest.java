package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
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
    void getByCategory_ShouldReturnListOfAdverts_WhenCategoryExists() {
        Category category = Category.ELECTRONICS;
        List<Advert> adverts = List.of(new Advert(), new Advert());
        List<AdvertBasicInfoDTO> expectedDTOs = List.of(new AdvertBasicInfoDTO(), new AdvertBasicInfoDTO());

        when(advertRepository.findAllByCategory(category)).thenReturn(adverts);
        when(modelMapper.map(any(Advert.class), eq(AdvertBasicInfoDTO.class)))
                .thenReturn(new AdvertBasicInfoDTO());

        List<AdvertBasicInfoDTO> result = advertService.getByCategory(category);

        assertEquals(expectedDTOs, result);
        verify(advertRepository).findAllByCategory(category);
        verify(modelMapper, times(2)).map(any(Advert.class), eq(AdvertBasicInfoDTO.class));
    }

}

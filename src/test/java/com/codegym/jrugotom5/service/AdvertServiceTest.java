package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertCreateDTO;
import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.UserNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdvertServiceTest {

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdvertService advertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAdverts_OneAdvert() {
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

        List<AdvertDTO> actualAdverts = advertService.getAdvertsByDateRange(from, to);

        List<AdvertDTO> expectedAdverts = Arrays.asList(advertDTO1, advertDTO2);

        assertIterableEquals(expectedAdverts, actualAdverts, "The adverts list should match the expected list in both order and content");
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
    void createAdvert_userExists_advertCreatedSuccessfully() {
        AdvertCreateDTO advertCreateDTO = new AdvertCreateDTO();
        advertCreateDTO.setTitle("Test Title");
        advertCreateDTO.setDescription("Test Description");
        advertCreateDTO.setAuthorId(1L);
        advertCreateDTO.setEndDate(LocalDate.now().plusDays(10));
        advertCreateDTO.setIsActive(true);

        when(userService.userExistsById(1L)).thenReturn(true);

        Advert advertEntity = new Advert();
        when(modelMapper.map(advertCreateDTO, Advert.class)).thenReturn(advertEntity);

        advertService.createAdvert(advertCreateDTO);

        verify(advertRepository).save(any(Advert.class));
    }

    @Test
    void createAdvert_userNotFound_throwsUserNotFoundException() {
        AdvertCreateDTO advertCreateDTO = new AdvertCreateDTO();
        advertCreateDTO.setTitle("Test Title");
        advertCreateDTO.setDescription("Test Description");
        advertCreateDTO.setAuthorId(1L);
        advertCreateDTO.setEndDate(LocalDate.now().plusDays(10));
        advertCreateDTO.setIsActive(true);

        when(userService.userExistsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> advertService.createAdvert(advertCreateDTO));

        verify(advertRepository, never()).save(any(Advert.class));
    }

}

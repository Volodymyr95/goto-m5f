package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.dto.AdvertUpdateDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.InvalidIdException;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdvertServiceTest {

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private  UserRepository userRepository;

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
    void testSuccessfulUpdate() {

        Long advertId = 1L;
        AdvertUpdateDTO advertDTO = new AdvertUpdateDTO();
        advertDTO.setTitle("Updated Title");
        advertDTO.setDescription("Updated Description");

        Advert existingAdvert = new Advert();
        existingAdvert.setId(advertId);
        existingAdvert.setTitle("Old Title");
        existingAdvert.setDescription("Old Description");

        Advert savedAdvert = new Advert();
        savedAdvert.setId(advertId);
        savedAdvert.setTitle("Updated Title");
        savedAdvert.setDescription("Updated Description");

        AdvertUpdateDTO mappedAdvertDTO = new AdvertUpdateDTO();
        mappedAdvertDTO.setTitle("Updated Title");
        mappedAdvertDTO.setDescription("Updated Description");


        when(advertRepository.findById(advertId)).thenReturn(Optional.of(existingAdvert));
        when(advertRepository.save(existingAdvert)).thenReturn(savedAdvert);
        when(modelMapper.map(savedAdvert, AdvertUpdateDTO.class)).thenReturn(mappedAdvertDTO);


        AdvertUpdateDTO result = advertService.update(advertId, advertDTO);


        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());


        verify(advertRepository, times(1)).findById(advertId);
        verify(advertRepository, times(1)).save(existingAdvert);
    }

    @Test
    void testAdvertNotFound() {

        Long advertId = 1L;
        AdvertUpdateDTO advertDTO = new AdvertUpdateDTO();
        advertDTO.setTitle("Updated Title");
        advertDTO.setDescription("Updated Description");

        when(advertRepository.findById(advertId)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> advertService.update(advertId, advertDTO));

        verify(advertRepository, never()).save(any(Advert.class));
    }

}

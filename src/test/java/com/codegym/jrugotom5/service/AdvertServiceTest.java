package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.User;
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
    public void testUpdateShouldUpdateAdvertWhenAdvertAndUserExist() {

        AdvertDTO advertDTO = new AdvertDTO();
        advertDTO.setId(1L);
        advertDTO.setUserId(1L);

        Advert advert = new Advert();
        advert.setId(1L);

        User user = new User();
        user.setId(1L);

        when(advertRepository.findById(1L)).thenReturn(Optional.of(advert));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(advertRepository.save(any(Advert.class))).thenReturn(advert);
        when(modelMapper.map(any(AdvertDTO.class), eq(Advert.class))).thenReturn(advert);
        when(modelMapper.map(any(Advert.class), eq(AdvertDTO.class))).thenReturn(advertDTO);


        AdvertDTO updatedAdvertDTO = advertService.update(advertDTO);


        assertNotNull(updatedAdvertDTO);
        assertEquals(1L, updatedAdvertDTO.getId());
        verify(advertRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(advertRepository).save(any(Advert.class));
    }

    @Test
    public void testUpdateShouldThrowExceptionWhenAdvertDoesNotExist() {

        AdvertDTO advertDTO = new AdvertDTO();
        advertDTO.setId(1L);
        advertDTO.setUserId(1L);

        when(advertRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(InvalidIdException.class, () -> advertService.update(advertDTO));
        verify(advertRepository).findById(1L);
        verify(userRepository, never()).findById(anyLong());
        verify(advertRepository, never()).save(any(Advert.class));
    }

    @Test
    public void testUpdateShouldThrowExceptionWhenUserDoesNotExist() {

        AdvertDTO advertDTO = new AdvertDTO();
        advertDTO.setId(1L);
        advertDTO.setUserId(1L);

        Advert advert = new Advert();
        advert.setId(1L);

        when(advertRepository.findById(1L)).thenReturn(Optional.of(advert));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(InvalidIdException.class, () -> advertService.update(advertDTO));
        verify(advertRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(advertRepository, never()).save(any(Advert.class));
    }


}

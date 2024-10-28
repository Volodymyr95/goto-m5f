package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertCreateDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.UserNotFoundException;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdvertServiceTest {

    @Mock
    private UserRepository userRepository;

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

        int numberOfAdverts = 2;
        List<Advert> adverts = new ArrayList<>(numberOfAdverts);
        List<AdvertFullInfoDTO> expectedDtoList = new ArrayList<>(numberOfAdverts);

        when(advertRepository.findAllByCreatedDateBetween(from, to)).thenReturn(adverts);

        for (int i = 0; i < numberOfAdverts; i++) {
            String advertTitle = "Advert " + i;

            Advert advert = new Advert();
            User user = new User();
            user.setId((long) i);
            advert.setTitle(advertTitle);
            advert.setCreatedBy(user);
            adverts.add(advert);

            AdvertFullInfoDTO advertDTO = new AdvertFullInfoDTO();
            advertDTO.setId((long) i);
            advertDTO.setTitle(advertTitle);
            expectedDtoList.add(advertDTO);

            when(modelMapper.map(advert, AdvertFullInfoDTO.class)).thenReturn(advertDTO);
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
    void getByTitleContains_ShouldReturnListOfAdverts_WhenAdvertsWithPhraseFoundInDb() {
        String phrase = "Laptop";
        List<Advert> adverts = List.of(new Advert(), new Advert());
        List<AdvertBasicInfoDTO> expectedDTOs = List.of(new AdvertBasicInfoDTO(), new AdvertBasicInfoDTO());

        when(advertRepository.findAllByTitleContainsIgnoreCase(phrase)).thenReturn(adverts);
        when(modelMapper.map(any(Advert.class), eq(AdvertBasicInfoDTO.class)))
                .thenReturn(new AdvertBasicInfoDTO());

        List<AdvertBasicInfoDTO> result = advertService.getByTitleContains(phrase);

        assertEquals(expectedDTOs.size(), result.size());
        verify(advertRepository).findAllByTitleContainsIgnoreCase(phrase);
        verify(modelMapper, times(2)).map(any(Advert.class), eq(AdvertBasicInfoDTO.class));
    }

    @Test
    void getByTitleContains_ShouldReturnEmptyList_WhenNoAdvertsFound() {
        String phrase = "NonExistingTitle";
        when(advertRepository.findAllByTitleContainsIgnoreCase(phrase)).thenReturn(Collections.emptyList());

        List<AdvertBasicInfoDTO> result = advertService.getByTitleContains(phrase);

        assertTrue(result.isEmpty());
        verify(advertRepository).findAllByTitleContainsIgnoreCase(phrase);
        verify(modelMapper, never()).map(any(Advert.class), any(Class.class));
    }

    @Test
    void createAdvert_userExists_advertCreatedSuccessfully() {
        AdvertCreateDTO advertCreateDTO = new AdvertCreateDTO();
        advertCreateDTO.setTitle("Test Title");
        advertCreateDTO.setDescription("Test Description");
        advertCreateDTO.setUserCreatorId(1L);

        User user = new User();
        user.setId(1L);

        Advert advertEntity = new Advert();
        advertEntity.setTitle(advertCreateDTO.getTitle());
        advertEntity.setDescription(advertCreateDTO.getDescription());
        advertEntity.setCreatedDate(LocalDate.now());
        advertEntity.setEndDate(LocalDate.now().plusDays(30));
        advertEntity.setIsActive(true);
        advertEntity.setCreatedBy(user);

        Advert advert = new Advert();
        advert.setId(1L);

        AdvertFullInfoDTO advertFullInfoDTO = new AdvertFullInfoDTO();
        advertFullInfoDTO.setUserCreatorId(1L);

        when(userService.userExistsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(advertRepository.save(any(Advert.class))).thenReturn(advert);
        when(modelMapper.map(advert, AdvertFullInfoDTO.class)).thenReturn(advertFullInfoDTO);

        AdvertFullInfoDTO result = advertService.createAdvert(advertCreateDTO);

        verify(userService).userExistsById(1L);
        verify(userRepository).findById(1L);
        verify(advertRepository).save(any(Advert.class));
        verify(modelMapper).map(advert, AdvertFullInfoDTO.class);

        assertNotNull(result);
        assertEquals(1L, result.getUserCreatorId());
    }

    @Test
    void createAdvert_userNotFound_throwsUserNotFoundException() {
        AdvertCreateDTO advertCreateDTO = new AdvertCreateDTO();
        advertCreateDTO.setTitle("Test Title");
        advertCreateDTO.setDescription("Test Description");
        advertCreateDTO.setUserCreatorId(1L);

        when(userService.userExistsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> advertService.createAdvert(advertCreateDTO));

        verify(advertRepository, never()).save(any(Advert.class));
    }
}

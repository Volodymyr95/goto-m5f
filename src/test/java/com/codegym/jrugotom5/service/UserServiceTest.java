package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserInfoDTO;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_OneUser() {
        UserInfoDTO expectedDto = new UserInfoDTO();
        expectedDto.setId(1L);
        expectedDto.setFirstName("John");
        expectedDto.setLastName("Smith");

        when(userRepository.findAll()).thenReturn(List.of(new User()));
        when(modelMapper.map(any(User.class), eq(UserInfoDTO.class))).thenReturn(expectedDto);

        UserInfoDTO dtoFromService = userService.getAllUsers().get(0);

        assertEquals(expectedDto, dtoFromService);

        verify(userRepository).findAll();
        verify(modelMapper).map(any(User.class), eq(UserInfoDTO.class));
    }
}

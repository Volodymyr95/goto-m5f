package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, modelMapper);
    }

    @Test
    public void testGetAll_OneUser() {
        UserBasicInfoDTO expectedDto = new UserBasicInfoDTO();
        expectedDto.setId(1L);
        expectedDto.setFirstName("John");
        expectedDto.setLastName("Smith");

        when(userRepository.findAll()).thenReturn(List.of(new User()));
        when(modelMapper.map(any(User.class), eq(UserBasicInfoDTO.class))).thenReturn(expectedDto);

        List<UserBasicInfoDTO> dtoListFromService = userService.getAllUsers();

        assertEquals(List.of(expectedDto), dtoListFromService);

        verify(userRepository).findAll();
    }
}

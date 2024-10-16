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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        User user = new User();
        List<User> oneUser = List.of(user);

        when(userRepository.findAll()).thenReturn(oneUser);
        when(modelMapper.map(user, UserInfoDTO.class)).thenReturn(new UserInfoDTO());

        List<UserInfoDTO> usersFromService = userService.getAllUsers();

        assertEquals(1, usersFromService.size());

        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user, UserInfoDTO.class);
    }

    @Test
    public void testGetAll_NoUsers() {
        ArrayList<User> emptyUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(emptyUsers);

        List<UserInfoDTO> usersFromService = userService.getAllUsers();
        assertEquals(0, usersFromService.size());

        verify(userRepository, times(1)).findAll();
        verify(modelMapper, never()).map(any(), eq(UserInfoDTO.class));
    }
}

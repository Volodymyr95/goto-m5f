package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.exception.UserDeletionException;
import com.codegym.jrugotom5.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    private UserService userService;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, modelMapper);

        // creating a test user
        user = new User();
        user.setId(1L);
        user.setEmail("john.smith@example.com");
        user.setFirstName("John");
        user.setLastName("Smith");
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

    @Test
    public void deleteUserById_ExistingId_ShouldDeleteUser() {
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUserById(user.getId());

        verify(userRepository).deleteById(user.getId());
    }

    @Test
    public void deleteUserById_NonExistingId_ShouldThrowException() {
        when(userRepository.existsById(user.getId())).thenReturn(false);

        UserDeletionException exception = assertThrows(UserDeletionException.class, () -> {
            userService.deleteUserById(user.getId());
        });
        assertEquals("User with 1 ID not found", exception.getMessage());
    }

    @Test
    public void deleteUserByEmail_ExistingEmail_ShouldDeleteUser() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        userService.deleteUserByEmail(user.getEmail());

        verify(userRepository).deleteByEmail(user.getEmail());
    }

    @Test
    public void deleteUserByEmail_NonExistingEmail_ShouldThrowException() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        UserDeletionException exception = assertThrows(UserDeletionException.class, () -> {
            userService.deleteUserByEmail(user.getEmail());
        });
        assertEquals("User with email john.smith@example.com not found", exception.getMessage());
    }

    @Test
    public void deleteUserByName_ExistingName_ShouldDeleteUser() {
        when(userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())).thenReturn(true);

        userService.deleteUserByName(user.getFirstName(), user.getLastName());

        verify(userRepository).deleteByFirstNameAndLastName(user.getFirstName(), user.getLastName());
    }

    @Test
    public void deleteUserByName_NonExistingName_ShouldThrowException() {
        when(userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())).thenReturn(false);

        UserDeletionException exception = assertThrows(UserDeletionException.class, () -> {
            userService.deleteUserByName(user.getFirstName(), user.getLastName());
        });
        assertEquals("User with firstname John and lastname Smith not found", exception.getMessage());
    }

}

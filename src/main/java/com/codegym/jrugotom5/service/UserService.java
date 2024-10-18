package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserBasicInfoDTO> getAllUsers() {
        List<User> allUsers = (List<User>) userRepository.findAll();
        return allUsers.stream()
                .map(user -> modelMapper.map(user, UserBasicInfoDTO.class))
                .toList();
    }

    public boolean userExistsById(Long id) {
        return userRepository.existsById(id);
    }
}

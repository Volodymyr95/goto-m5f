package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserInfoDTO;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.exception.UserFetchException;
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

    public List<UserInfoDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            log.error("No Users found");
            throw new UserFetchException("No Users found");
        }
        return allUsers.stream()
                .map(user -> modelMapper.map(user, UserInfoDTO.class))
                .toList();
    }
}

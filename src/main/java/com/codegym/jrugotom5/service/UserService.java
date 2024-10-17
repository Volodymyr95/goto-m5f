package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserBasicInfoDTO> getAllUsers() {
        List<UserBasicInfoDTO> usersDto = new ArrayList<>();
        userRepository.findAll().forEach(user ->
                usersDto.add(modelMapper.map(user, UserBasicInfoDTO.class)));
        return usersDto;
    }
}

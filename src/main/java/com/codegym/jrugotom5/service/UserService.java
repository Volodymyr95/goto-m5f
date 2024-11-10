package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.exception.InavlidUserDelException;
import com.codegym.jrugotom5.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserBasicInfoDTO> getAllUsers() {
        return Streamable.of(userRepository.findAll())
                .map(user -> modelMapper.map(user, UserBasicInfoDTO.class))
                .toList();
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new InavlidUserDelException("User with %d ID not found".formatted(id));
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new InavlidUserDelException("User with email %s not found".formatted(email));
        }
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public void deleteUserByName(String firstName, String lastName) {
        if (!userRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new InavlidUserDelException("User with firstname %s and lastname %s not found"
                    .formatted(firstName, lastName));
        }
        userRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }

}

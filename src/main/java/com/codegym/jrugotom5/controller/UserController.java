package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserBasicInfoDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @Transactional
    @DeleteMapping("/user/email/{email}")
    public void deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
    }

    @Transactional
    @DeleteMapping("/user/name")
    public void deleteUserByName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        userService.deleteUserByName(firstName, lastName);
    }

}

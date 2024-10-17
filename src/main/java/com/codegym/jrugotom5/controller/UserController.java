package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.UserNameDTO;
import com.codegym.jrugotom5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserNameDTO> getUsers() {
        return userService.getAllUsers();
    }
}

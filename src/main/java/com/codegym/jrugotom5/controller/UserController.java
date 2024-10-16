package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.UserInfoDTO;
import com.codegym.jrugotom5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @RequestMapping("/all")
    public List<UserInfoDTO> getUsers() {
        return userService.getAllUsers();
    }
}

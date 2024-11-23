package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.dto.UserBasicInfoDTO;
import com.codegym.jrugotom5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserBasicInfoDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping("/email/{email}")
    public void deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
    }

    @DeleteMapping("/name")
    public void deleteUserByName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        userService.deleteUserByName(firstName, lastName);
    }

}

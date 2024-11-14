package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.User;
import com.codegym.jrugotom5.repository.AdvertRepository;
import com.codegym.jrugotom5.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/clear_h2.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AdvertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @SneakyThrows
    public void testGetAllAdverts() {
        List<Advert> adverts = List.of(new Advert(), new Advert());
        advertRepository.saveAll(adverts);

        mockMvc.perform(get("/api/adverts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(adverts.size())));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByDateRange() {
        int numberOfAdverts = 3;
        User user = new User();
        userRepository.save(user);

        List<Advert> adverts = new ArrayList<>();
        for (int i = 0; i < numberOfAdverts; i++) {
            Advert advert = new Advert();
            advert.setCreatedDate(LocalDate.of(2024, (i + 1) % 12, (i + 1) % 30));
            advert.setCreatedBy(user);
            adverts.add(advert);
        }
        advertRepository.saveAll(adverts);

        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2025, 1, 1);
        mockMvc.perform(get("/api/adverts/date")
                        .param("from", from.toString())
                        .param("to", to.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(numberOfAdverts)));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByDateRange_EmptyDates() {
        mockMvc.perform(get("/api/adverts/date")
                        .param("from", " ")
                        .param("to", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByDateRange_FromHigherThanTo() {
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 1);
        mockMvc.perform(get("/api/adverts/date")
                        .param("from", from.toString())
                        .param("to", to.toString()))
                .andExpect(status().isBadRequest());
    }


    @Test
    @SneakyThrows
    public void testGetAdvertsByUser_ValidUserId_ReturnsAdverts() {
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        Advert advert1 = new Advert();
        advert1.setCreatedBy(user);
        Advert advert2 = new Advert();
        advert2.setCreatedBy(user);
        advertRepository.saveAll(List.of(advert1, advert2));

        mockMvc.perform(get("/api/adverts/user").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByUser_InvalidUserId_ReturnsBadRequest() {
        mockMvc.perform(get("/api/adverts/user").param("id", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByUser_UserNotFound_ReturnsNotFound() {
        mockMvc.perform(get("/api/adverts/user").param("id", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void testGetAdvertById() {
        User testUser = new User();
        testUser.setEmail("test email");
        userRepository.save(testUser);

        Advert testAdvert = new Advert();
        testAdvert.setCreatedBy(testUser);
        testAdvert.setDescription("Test Advert");
        testAdvert.setCreatedDate(LocalDate.of(2023, 1, 1));
        testAdvert.setTitle("test advert title");

        Long testAdvertId = advertRepository.save(testAdvert).getId();

        mockMvc.perform(get("/api/adverts/" + testAdvertId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(testAdvert.getDescription())))
                .andExpect(jsonPath("$.createdDate", is(testAdvert.getCreatedDate().toString())))
                .andExpect(jsonPath("$.title", is(testAdvert.getTitle())));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertByIdWithInvalidId() {
        long testAdvertId = -1L;
        mockMvc.perform(get("/api/adverts/" + testAdvertId))
                .andExpect(status().isBadRequest());
    }
}
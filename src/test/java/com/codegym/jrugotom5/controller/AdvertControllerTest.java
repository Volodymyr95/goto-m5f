package com.codegym.jrugotom5.controller;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
        for (int i = 0; i < numberOfAdverts; i++) {
            Advert advert = new Advert();
            advert.setCreatedDate(LocalDate.of(2024,(i + 1) % 12,(i + 1) % 30));
            advert.setCreatedBy(user);
            advertRepository.save(advert);
        }

        LocalDate from = LocalDate.of(2024,1,1);
        LocalDate to = LocalDate.of(2025,1,1);
        mockMvc.perform(get("/api/adverts/date")
                        .param("from",from.toString())
                        .param("to",to.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(numberOfAdverts)));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByDateRange_EmptyDates() {
        mockMvc.perform(get("/api/adverts/date")
                        .param("from"," ")
                        .param("to"," "))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByDateRange_FromHigherThanTo() {
        LocalDate from = LocalDate.of(2025,1,1);
        LocalDate to = LocalDate.of(2024,1,1);
        mockMvc.perform(get("/api/adverts/date")
                        .param("from",from.toString())
                        .param("to",to.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByTitlePhrase() {
        Advert advert1 = new Advert();
        advert1.setTitle("test");
        Advert advert2 = new Advert();
        advert2.setTitle("TeSt");
        Advert advert3 = new Advert();
        advert3.setTitle("another title");

        List<Advert> adverts = List.of(advert1, advert2, advert3);
        advertRepository.saveAll(adverts);

        mockMvc.perform(get("/api/adverts/adverts").param("title","test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByCategory() {
        Advert advert1 = new Advert();
        advert1.setCategory(Category.VEHICLES);
        Advert advert2 = new Advert();
        advert2.setCategory(Category.VEHICLES);
        Advert advert3 = new Advert();
        advert3.setCategory(Category.REAL_ESTATE);

        List<Advert> adverts = List.of(advert1, advert2, advert3);
        advertRepository.saveAll(adverts);

        mockMvc.perform(get("/api/adverts/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    public void testGetAdvertsByCategoryInvalidCategory() {
        mockMvc.perform(get("/api/adverts/bad_category"))
                .andExpect(status().isBadRequest());
    }
}
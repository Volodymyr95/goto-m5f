package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;

    public List<Advert> getAdvertsByDateRange(LocalDate from, LocalDate to) {

        return this.advertRepository.findAllByCreatedDateBetween(from, to);
    }
}

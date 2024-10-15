package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;

    public List<Advert> getAdvertsByDateRange(Date from, Date to) {

        return this.advertRepository.findAllByCreatedDateBetween(from, to);
    }
}

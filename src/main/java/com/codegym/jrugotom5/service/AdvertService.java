package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdvertService {

        AdvertRepository advertRepository;
        public AdvertService( @Autowired AdvertRepository advertRepository ) {
            this.advertRepository = advertRepository;
        }


    public List<Advert> getAdvertsByDateRange(Date from, Date to) {

            return this.advertRepository.findAllByCreatedDateBetween(from, to);
    }
}

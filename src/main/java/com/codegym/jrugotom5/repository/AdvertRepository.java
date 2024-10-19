package com.codegym.jrugotom5.repository;

import com.codegym.jrugotom5.entity.Advert;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
    List<Advert> findAllByCreatedDateBetweenFromTo(LocalDate from, LocalDate to);
    
    List<Advert> getAdvertsByCreatedBy_Id (Long createdBy_id);
}

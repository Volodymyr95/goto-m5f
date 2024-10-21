package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertCreateDTO;
import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.exception.InvalidDateRangeException;
import com.codegym.jrugotom5.exception.UserNotFoundException;
import com.codegym.jrugotom5.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public List<AdvertDTO> getAdvertsByDateRange(LocalDate from, LocalDate to) {

        if (from.isAfter(to) || from.isEqual(to)) {
            throw new InvalidDateRangeException("'From' date should be after 'To' date.");
        }
        List<Advert> adverts = this.advertRepository.findAllByCreatedDateBetweenFromTo(from, to);

        return adverts.stream()
                .map(advert -> modelMapper.map(advert, AdvertDTO.class))
                .collect(Collectors.toList());
    }

    public List<AdvertBasicInfoDTO> getAllAdverts() {
        return Streamable.of(advertRepository.findAll())
                .map(advert->modelMapper.map(advert, AdvertBasicInfoDTO.class))
                .toList();
    }

    @Transactional
    public void createAdvert(AdvertCreateDTO advertCreateDTO) {
        if(!userService.userExistsById(advertCreateDTO.getAuthorId())) {
            log.error("Could not find User with id {}", advertCreateDTO.getAuthorId());
            throw new UserNotFoundException("User with id " + advertCreateDTO.getAuthorId() + " does not exist.");
        }
        Advert advertEntity = modelMapper.map(advertCreateDTO, Advert.class);
        advertEntity.setCreatedDate(LocalDate.now());
        advertEntity.setCreatedBy(userService.getUserById(advertCreateDTO.getAuthorId()));
        advertRepository.save(advertEntity);
    }
}

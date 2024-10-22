package com.codegym.jrugotom5.service;

import com.codegym.jrugotom5.dto.AdvertBasicInfoDTO;
import com.codegym.jrugotom5.dto.AdvertCreateDTO;
import com.codegym.jrugotom5.dto.AdvertFullInfoDTO;
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

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public List<AdvertFullInfoDTO> getAdvertsByDateRange(LocalDate from, LocalDate to) {

        if (from.isAfter(to) || from.isEqual(to)) {
            throw new InvalidDateRangeException("'From' date should be after 'To' date.");
        }
        List<Advert> adverts = this.advertRepository.findAllByCreatedDateBetween(from, to);

        return adverts.stream()
                .map(advert ->
                {
                    AdvertFullInfoDTO dto = modelMapper.map(advert, AdvertFullInfoDTO.class);
                    dto.setUserCreatorId(advert.getCreatedBy().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AdvertBasicInfoDTO> getAllAdverts() {
        return Streamable.of(advertRepository.findAll())
                .map(advert->modelMapper.map(advert, AdvertBasicInfoDTO.class))
                .toList();
    }

    @Transactional
    public AdvertFullInfoDTO createAdvert(AdvertCreateDTO advertCreateDTO) {
        Long id = advertCreateDTO.getUserCreatorId();
        if(!userService.userExistsById(id)) {
            log.error("Could not find User with id {}", id);
            throw new UserNotFoundException("User with id " + id + " does not exist.");
        }
        Advert advertEntity = modelMapper.map(advertCreateDTO, Advert.class);
        advertEntity.setCreatedDate(LocalDate.now());
        advertEntity.setEndDate(LocalDate.now().plusDays(30));
        advertEntity.setCreatedBy(userService.getUserById(id));
        advertRepository.save(advertEntity);
        return modelMapper.map(advertEntity, AdvertFullInfoDTO.class);
    }
}

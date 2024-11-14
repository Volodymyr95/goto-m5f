package com.codegym.jrugotom5.config;

import com.codegym.jrugotom5.service.AdvertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TasksScheduler {

    private final AdvertService advertService;

    @Transactional
    @Scheduled(cron = "${tasks-scheduler.make-adverts-inactive.cron}")
    public void makeAdvertsInactive() {
        advertService.deactivateExpiredAdverts();
        log.info("Daily task \"makeAdvertsInactive\" has done.");
    }

}

package com.ppio.bd.dcacheflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SyncTask {

    @Autowired
    private SyncService syncService;

    @Scheduled(cron = "0 0 6 * * ? ")
    public void sync() {
        String dt = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        syncService.sync(dt);
    }

}
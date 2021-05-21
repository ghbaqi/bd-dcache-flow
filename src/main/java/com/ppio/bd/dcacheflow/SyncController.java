package com.ppio.bd.dcacheflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    @Autowired
    private SyncService syncService;

    @GetMapping("/hi")
    public String hi() {
        return "hi 1112222";
    }


    @GetMapping("/sync")
    public String sync(String dt) {
        if (dt == null || dt.trim() == "" || dt.trim().length() != 8)
            return "非法 dt 参数";
        syncService.sync(dt);
        return "sync ing dt = " + dt;
    }
}

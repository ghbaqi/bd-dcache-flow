package com.ppio.bd.dcacheflow;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SyncServiceTest {

    @Autowired
    private SyncService service;

    @Test
    public void test(){
        service.sync("20210517");
    }
}

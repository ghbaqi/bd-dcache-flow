package com.ppio.bd.dcacheflow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

// curl -X GET -u painetwork:PPIOpaigod2021painet
// "https://dcache.iqiyi.com/api/flow?starttime=1621224956&endtime=1621235756&deviceid=000F27F7F0A45209"

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpTest01 {
    // {"code":0,"msg":"ok","data":[{"timestamp":"2021-05-17 12:20:00","downloadspeed":0,"uploadspeed":3622236},{"timestamp":"2021-05-17 12:25:00","downloadspeed":0,"uploadspeed":4326304},{"timestamp":"2021-05-17 12:30:00","downloadspeed":0,"uploadspeed":4378495},{"timestamp":"2021-05-17 12:35:00","downloadspeed":0,"uploadspeed":4452818},{"timestamp":"2021-05-17 12:40:00","downloadspeed":0,"uploadspeed":3886910},{"timestamp":"2021-05-17 12:45:00","downloadspeed":0,"uploadspeed":5152915},{"timestamp":"2021-05-17 12:50:00","downloadspeed":0,"uploadspeed":4482880},{"timestamp":"2021-05-17 12:55:00","downloadspeed":0,"uploadspeed":4022901},{"timestamp":"2021-05-17 13:00:00","downloadspeed":0,"uploadspeed":4897111},{"timestamp":"2021-05-17 13:05:00","downloadspeed":0,"uploadspeed":5032444},{"timestamp":"2021-05-17 13:10:00","downloadspeed":0,"uploadspeed":4184383},{"timestamp":"2021-05-17 13:15:00","downloadspeed":0,"uploadspeed":5345361},{"timestamp":"2021-05-17 13:20:00","downloadspeed":0,"uploadspeed":3988075},{"timestamp":"2021-05-17 13:25:00","downloadspeed":0,"uploadspeed":3776092},{"timestamp":"2021-05-17 13:30:00","downloadspeed":0,"uploadspeed":3925338},{"timestamp":"2021-05-17 13:35:00","downloadspeed":0,"uploadspeed":2618253},{"timestamp":"2021-05-17 13:40:00","downloadspeed":0,"uploadspeed":3948919},{"timestamp":"2021-05-17 13:45:00","downloadspeed":0,"uploadspeed":2184030},{"timestamp":"2021-05-17 13:50:00","downloadspeed":0,"uploadspeed":2660584},{"timestamp":"2021-05-17 13:55:00","downloadspeed":0,"uploadspeed":2276031},{"timestamp":"2021-05-17 14:00:00","downloadspeed":0,"uploadspeed":2673054},{"timestamp":"2021-05-17 14:05:00","downloadspeed":0,"uploadspeed":1961929},{"timestamp":"2021-05-17 14:10:00","downloadspeed":0,"uploadspeed":2603078},{"timestamp":"2021-05-17 14:15:00","downloadspeed":0,"uploadspeed":1900184},{"timestamp":"2021-05-17 14:20:00","downloadspeed":0,"uploadspeed":2172644},{"timestamp":"2021-05-17 14:25:00","downloadspeed":0,"uploadspeed":1776369},{"timestamp":"2021-05-17 14:30:00","downloadspeed":0,"uploadspeed":2344528},{"timestamp":"2021-05-17 14:35:00","downloadspeed":0,"uploadspeed":1900194},{"timestamp":"2021-05-17 14:40:00","downloadspeed":0,"uploadspeed":2809641},{"timestamp":"2021-05-17 14:45:00","downloadspeed":0,"uploadspeed":1601439},{"timestamp":"2021-05-17 14:50:00","downloadspeed":0,"uploadspeed":1433046},{"timestamp":"2021-05-17 14:55:00","downloadspeed":0,"uploadspeed":2099022},{"timestamp":"2021-05-17 15:00:00","downloadspeed":0,"uploadspeed":1795390},{"timestamp":"2021-05-17 15:05:00","downloadspeed":56977740,"uploadspeed":1713047},{"timestamp":"2021-05-17 15:10:00","downloadspeed":42498660,"uploadspeed":2648972},{"timestamp":"2021-05-17 15:15:00","downloadspeed":51609708,"uploadspeed":2179024}]}
    @Autowired
    private RestTemplate restTemplate;
    String url = "https://dcache.iqiyi.com/api/flow?starttime=1621224956&endtime=1621235756&deviceid=000F27F7F0A45209";

//     while it seems to fit format 'yyyy-MM-dd'T'HH:mm:ss.SSSX', parsing fails
    @Test
    public void test01() {
        log.info("restTemplate = " + restTemplate);
        ResponseEntity<FlowResult> responseEntity = restTemplate.getForEntity(url, FlowResult.class);
        System.out.println(JSON.toJSONString(responseEntity.getBody()));
    }
}

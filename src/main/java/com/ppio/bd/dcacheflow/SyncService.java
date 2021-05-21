package com.ppio.bd.dcacheflow;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SyncService {

    @Value("${dcache.kafka.topic}")
    private   String kafkaTopic;


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private RestTemplate restTemplate;
    private static final String dcache_url = "https://dcache.iqiyi.com/api/flow?starttime=%s&endtime=%s&deviceid=%s";

    private static final String get_customid_sql = "SELECT DISTINCT custom_id FROM t_ods_docker_monitor_info  WHERE dt='%s' AND name='dcache' ;";

    /**
     * 1. 通过 holo 获取所有 爱奇艺实例 id
     * SELECT DISTINCT custom_id FROM t_ods_docker_monitor_info
     * WHERE dt='20210501' AND name='dcache' ;
     * <p>
     * 2. 通过爱奇艺接口 获取带宽数组
     * 3. 发送到 kafka
     */

    public static void main(String[] args) {
        String dt = "20210501";
        LocalDate date = LocalDate.parse(dt, DateTimeFormatter.ofPattern("yyyyMMdd"));
        long startTime = date.atTime(0, 0, 0).toEpochSecond(ZoneOffset.of("+8"));
        long endTime = date.atTime(23, 59, 59).toEpochSecond(ZoneOffset.of("+8"));
        log.info(startTime + "   " + endTime);
    }

    @Async
    public void sync(String dt) {

        Connection conn = null;
        Statement stmt = null;
        List<String> customIds = new ArrayList<>(512);
        try {
            Class.forName("org.postgresql.Driver");

//            System.out.println(Driver.class.getName());
            conn = DriverManager.getConnection("jdbc:postgresql://hgmc-cn-st21vo0do001-cn-hangzhou.hologres.aliyuncs.com:80/db_test?preferQueryMode=simple&tcpKeepAlive=true", "LTAI4GFy8z6y9KYpuGUpJQRU", "aCSMTvu2vJP2rfSFWYSxxgJUzevAr1");
            System.out.println("连接数据库成功!");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(get_customid_sql, dt));
            while (rs.next()) {
                String custom_id = rs.getString("custom_id");
                if (custom_id != null && custom_id.trim() != "") {
                    customIds.add(custom_id);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            log.error("hologres 出错 {}", e);
        }
        log.info("dt = {} , customIds = {}", dt, customIds);
//        long startTime = 1621224956;
//        long endTime = 1621235756;
        LocalDate date = LocalDate.parse(dt, DateTimeFormatter.ofPattern("yyyyMMdd"));

        long startTime = date.atTime(0, 0, 0).toEpochSecond(ZoneOffset.of("+8")); // 作为分区时间戳
        long endTime = date.atTime(23, 59, 59).toEpochSecond(ZoneOffset.of("+8"));

        for (String customId : customIds) {

            //
            ResponseEntity<FlowResult> responseEntity = restTemplate.getForEntity(String.format(dcache_url, startTime, endTime, customId), FlowResult.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                log.error("获取实例数据错误1 custom_id = {} , error = {} ", customId, responseEntity.getStatusCode().getReasonPhrase());
                continue;
            }
            FlowResult flowResult = responseEntity.getBody();
            if (flowResult.getCode() != 0) {
                log.error("获取实例数据错误2 custom_id = {} , msg = {} ", customId, flowResult.getMsg());
                continue;
            }
            List<FlowResult.FlowBean> data = flowResult.getData();
            for (FlowResult.FlowBean flowBean : data) {
//                log.info("customId = {} , timestamp = {} , uploadspeed = {}", customId, flowBean.getTimestamp(), flowBean.getUploadspeed());
                // TODO 将机器 流量信息发送到 kafka
                FlowMsgBean msgBean = new FlowMsgBean(dt, customId, flowBean.getTimestamp(), flowBean.getDownloadspeed(), flowBean.getUploadspeed());
                kafkaTemplate.send(kafkaTopic, JSON.toJSONString(msgBean));
                log.info("msg = {}", JSON.toJSONString(msgBean));

            }


        }


    }


}

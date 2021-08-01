package com.ppio.bd.dcacheflow;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * mvn  clean  package  -Dmaven.test.skip=true
 * nohup java -jar   dcache-flow-0.0.1-SNAPSHOT.jar > /dev/null 2>&1  &
 *
 *
 * wget   https://pi-platform-deploy.oss-cn-hangzhou.aliyuncs.com/bigdata/dcache-flow-0.0.1-SNAPSHOT.jar
 */

/**
 * pi-kafka-consumer-01   /opt/
 *
 * curl 127.0.0.1:33445/sync?dt=20210703
 */
@EnableKafka
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class DcacheFlowApplication {

    @Bean
    public RestTemplate restTemplate() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("painetwork", "PPIOpaigod2021painet"));
//                new UsernamePasswordCredentials("painetwork", "pai2021PPIOdCACHE#%"));
                new UsernamePasswordCredentials("painetwork", "pai20210730PPIOdCACHE#%"));

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .useSystemProperties()
                .build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);

        return new RestTemplate(requestFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(DcacheFlowApplication.class, args);
    }

}

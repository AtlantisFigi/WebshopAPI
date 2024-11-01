package com.example.webshopapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
class WebshopApiApplicationTests {

    @Test
    void contextLoads() {
    }

}

package com.example.webshopapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.properties")
class WebshopApiApplicationTests {

    @Test
    void contextLoads() {
    }
}

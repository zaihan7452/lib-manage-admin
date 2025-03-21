package com.hanzai.app.it.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerITTests {

    @Value("${spring.application.name}")
    private String applicationName;
    public static final String HELLO_PATH = "/v1/hello";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHello() {
        ResponseEntity<String> response = restTemplate.getForEntity(HELLO_PATH, String.class);

        log.info("test hello response status code: {}", response.getStatusCode());
        assert(response.getStatusCode()).is2xxSuccessful();
        log.info("test hello response body: {}", response.getBody());
        assert(response.getBody()).equals("hello " + applicationName);
    }

}

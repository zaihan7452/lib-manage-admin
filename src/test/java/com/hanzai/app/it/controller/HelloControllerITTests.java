package com.hanzai.app.it.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanzai.app.constant.LibManageAdminConstant;
import com.hanzai.app.dto.LoginResponse;
import com.hanzai.app.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Collections;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerITTests {

    @Value("${spring.application.name}")
    private String applicationName;

    public static final String HELLO_PATH = "/v1/hello";
    public static final String LOGIN_PATH = "/v1/auth/login";

    @Autowired
    private TestRestTemplate restTemplate;

    // Test for accessing the hello endpoint with JWT token
    @Test
    public void testHelloWithToken() {
        // Step 1: Login to get JWT Token
        String username = "hanzai";
        String password = "123456";

        // Construct the login request body
        String loginRequest = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

        // Send the login request to get the token
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> loginResponse = restTemplate.exchange(
                LOGIN_PATH,
                HttpMethod.POST,
                new HttpEntity<>(loginRequest, loginHeaders),
                String.class
        );

        log.info("Login response status code: {}", loginResponse.getStatusCode());
        assert(loginResponse.getStatusCode()).is2xxSuccessful();

        String token = extractTokenFromResponse(loginResponse);

        // Step 2: Access the protected resource with the token
        HttpHeaders helloHeaders = new HttpHeaders();
        helloHeaders.set(HttpHeaders.AUTHORIZATION, LibManageAdminConstant.BEARER + token);  // Set the Authorization Header

        HttpEntity<String> entity = new HttpEntity<>(helloHeaders);

        // Send the GET request to /v1/hello with the token
        ResponseEntity<String> response = restTemplate.exchange(
                HELLO_PATH,
                HttpMethod.GET,
                entity,
                String.class
        );

        log.info("Test hello response status code: {}", response.getStatusCode());
        assert(response.getStatusCode()).is2xxSuccessful();

        log.info("Test hello response body: {}", response.getBody());
        assert(response.getBody()).equals("hello " + applicationName);
    }

    // Test for accessing the hello endpoint without JWT token
    @Test
    public void testHelloWithoutToken() {
        // Step 1: Send the GET request to /v1/hello without the Authorization header
        ResponseEntity<String> response = restTemplate.exchange(
                HELLO_PATH,
                HttpMethod.GET,
                null, // No Authorization header
                String.class
        );

        log.info("Test hello without token response status code: {}", response.getStatusCode());
        // Step 2: Verify that access is denied (should return 401 Unauthorized)
        assert(response.getStatusCode()).equals(HttpStatus.UNAUTHORIZED);
    }

    // Helper method to extract the token from the login response
    private String extractTokenFromResponse(ResponseEntity<String> response) {
        try {
            // Using Jackson's ObjectMapper to map the response body to LoginResponse object
            ObjectMapper objectMapper = new ObjectMapper();

            // Deserialize the response body into a LoginResponse object
            Result<LoginResponse> loginResponse = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

            // Return the token from the LoginResponse object
            return loginResponse.getData().getToken();
        } catch (Exception e) {
            // Log error if any exception occurs during parsing
            log.error("Error extracting token from response: {}", e.getMessage());
            return null;
        }
    }
}

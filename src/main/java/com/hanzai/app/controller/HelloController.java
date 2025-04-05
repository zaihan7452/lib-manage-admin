package com.hanzai.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hello")
@Tag(name = "Hello API", description = "Hello API")
public class HelloController {

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("")
    @Operation(summary = "Hello API", description = "Returns a hello message with the application name")
    public String hello() {
        return "hello " + applicationName;
    }

}

package com.hanzai.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hello")
public class HelloController {

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("")
    public String hello() {
        return "hello " + applicationName;
    }

}

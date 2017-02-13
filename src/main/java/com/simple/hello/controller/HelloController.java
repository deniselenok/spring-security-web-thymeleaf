package com.simple.hello.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world!!!";
    }

}

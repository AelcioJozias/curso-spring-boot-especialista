package io.github.cursodsousa.libraryapi.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")

public class TestController {

    @GetMapping
    public Map<String, String> test() {
        return Map.of("message", "Hello World");
    }
}

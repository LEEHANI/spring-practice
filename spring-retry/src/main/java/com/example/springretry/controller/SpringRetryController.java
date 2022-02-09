package com.example.springretry.controller;

import com.example.springretry.service.SpringRetryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringRetryController {

    private final SpringRetryService service;

    public SpringRetryController(SpringRetryService service) {
        this.service = service;
    }

    @GetMapping("retry")
    public String retryController() throws Exception {
        return service.retry();
    }

    @GetMapping("retry-template")
    public String retryTemplateController() throws Exception {
        return service.retryTemplate();
    }
}

package uk.gov.companieshouse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuppressionController {

    @RequestMapping("/")
    public String index() {
        return "Hello, World!";
    }
}
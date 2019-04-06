package com.queuewise.demo.controllers;

import com.queuewise.demo.services.DataGenerationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate")
public class DataGenerationController {

    private DataGenerationService dataGenerationService;

    public DataGenerationController(DataGenerationService dataGenerationService) {
        this.dataGenerationService = dataGenerationService;
    }

    @PostMapping("/random")
    public String generateNewData() {
        return dataGenerationService.generateRandomData();
    }

}

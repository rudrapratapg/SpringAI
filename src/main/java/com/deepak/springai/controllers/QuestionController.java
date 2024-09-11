package com.deepak.springai.controllers;

import com.deepak.springai.model.*;
import com.deepak.springai.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest stateOrCountry) {
        return openAIService.getCapital(stateOrCountry);
    }

    @PostMapping("capitalWithInfo")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest stateOrCountry) {
        return openAIService.getCapitalWithInfo(stateOrCountry);
    }

    @PostMapping("capitalWithInfoJson")
    public Answer getCapitalWithInfoJSON(@RequestBody GetCapitalRequest stateOrCountry) {
        return openAIService.getCapitalWithInfoJSON(stateOrCountry);
    }

    @PostMapping("capitalWithInfoFormat")
    public GetCapitalResponse getCapitalWithInfoFormat(@RequestBody GetCapitalRequest stateOrCountry) {
        return openAIService.getCapitalWithInfoFormat(stateOrCountry);
    }

    @PostMapping("cityInfoFormat")
    public GetCityResponse getCityInfoFormat(@RequestBody GetCityRequest cityName) {
        return openAIService.getCityInfoFormat(cityName);
    }
}

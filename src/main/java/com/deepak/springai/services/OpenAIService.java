package com.deepak.springai.services;

import com.deepak.springai.model.*;

public interface OpenAIService {

    public String getAnswer(String question);

    public Answer getAnswer(Question question);

    public Answer getCapital(GetCapitalRequest getCapitalRequest);

    public Answer getCapitalWithInfo(GetCapitalRequest stateOrCountry);

    public Answer getCapitalWithInfoJSON(GetCapitalRequest stateOrCountry);

    public GetCapitalResponse getCapitalWithInfoFormat(GetCapitalRequest stateOrCountry);

    public GetCityResponse getCityInfoFormat(GetCityRequest getCityRequest);
}

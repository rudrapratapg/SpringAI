package com.deepak.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCityResponse(@JsonPropertyDescription("The city has  apopulation of ```population```.\n" +
        "    The city is located in ```region```.\n" +
        "    The primary language spoken is ```language```.\n" +
        "    The currency used is ```currency```.") City city) {
}

package com.deepak.springai.services;

import com.deepak.springai.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService{

    private final ChatModel chatModel;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoPrompt;

    @Value("classpath:templates/get-capital-info-json.st")
    private Resource getCapitalInfoJsonPrompt;

    @Value("classpath:templates/get-capital-info-format.st")
    private Resource getCapitalInfoJsonFormat;

    @Value("classpath:templates/get-city-info-format.st")
    private Resource getCityInfoJsonFormat;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest stateOrCountry){
        PromptTemplate template = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt = template.create(Map.of("stateOrCountry", stateOrCountry.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfoJSON(GetCapitalRequest getCapitalRequest){
        System.out.println("stateOrCountry: " + getCapitalRequest.stateOrCountry());
        PromptTemplate template = new PromptTemplate(getCapitalInfoJsonPrompt);
        Prompt prompt = template.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);
        System.out.println("response:"+response.getResult().getOutput().getContent());
        String responseString;
        try{
            String responseContent = response.getResult().getOutput().getContent();
            String cleanedResponse = responseContent.replace("```json", "").replace("```", "").trim();
            JsonNode jsonNode = objectMapper.readTree(cleanedResponse);
            responseString = jsonNode.get("answer").asText();
            System.out.println("responseString: " + responseString);
        } catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return new Answer(responseString);
    }

    @Override
    public GetCapitalResponse getCapitalWithInfoFormat(GetCapitalRequest getCapitalRequest){
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();
        System.out.println("format: " + format);
        System.out.println("stateOrCountry: " + getCapitalRequest.stateOrCountry());
        PromptTemplate template = new PromptTemplate(getCapitalInfoJsonFormat);
        Prompt prompt = template.create(Map.of(
                "stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format
        ));
        ChatResponse response = chatModel.call(prompt);
        return converter.convert(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCityResponse getCityInfoFormat(GetCityRequest getCityRequest){
        BeanOutputConverter<GetCityResponse> converter = new BeanOutputConverter<>(GetCityResponse.class);
        String format = converter.getFormat();
        System.out.println("cityName : "+getCityRequest.cityName());
        PromptTemplate template = new PromptTemplate(getCityInfoJsonFormat);
        Prompt prompt = template.create(Map.of(
                "cityName", getCityRequest.cityName(),
                "format",format)
        );
        ChatResponse response = chatModel.call(prompt);
        return converter.convert(response.getResult().getOutput().getContent());
    }


}

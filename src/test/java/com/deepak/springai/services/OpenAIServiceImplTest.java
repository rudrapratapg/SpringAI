package com.deepak.springai.services;

import com.deepak.springai.model.Answer;
import com.deepak.springai.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenAIServiceImplTest {

    @Autowired
    OpenAIService openAIService;

    @Test
    void getAnswer(){
        String question="What are the chances of rain today?";
        String response = openAIService.getAnswer(question);
        System.out.println(response);
    }

    @Test
    void getAnswer2(){
        String question="Shall I tell Associate Talent Sourcer that I have recently learnt Spring AI from Udemy?";
        Question question1=new Question(question);
        Answer answer = openAIService.getAnswer(question1);
        System.out.println(answer.answer());
    }
}

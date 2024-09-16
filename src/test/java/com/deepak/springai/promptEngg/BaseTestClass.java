package com.deepak.springai.promptEngg;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
//import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by jt, Spring Framework Guru.
 */
@SpringBootTest
public class BaseTestClass {

    @Autowired
    OpenAiChatModel openAiChatModel;
//    OpenAiChatClient openAiChatClient;

    String chat(String prompt) {
        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        Prompt promptToSend = promptTemplate.create();

//        return openAiChatClient.call(promptToSend).getResult().getOutput().getContent();
        return openAiChatModel.call(promptToSend).getResult().getOutput().getContent();
    }

}

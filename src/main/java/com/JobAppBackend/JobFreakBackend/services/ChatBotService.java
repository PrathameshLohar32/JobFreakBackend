package com.JobAppBackend.JobFreakBackend.services;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import reactor.core.publisher.Flux;

@Service
public class ChatBotService {
    private final ChatClient client;

    public ChatBotService(ChatClient.Builder builder){
        this.client = builder.build();
    }

    public Flux<String> getAnswer(String question){
        return this.client.prompt().user(question).stream().content();
    }

}

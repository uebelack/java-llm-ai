package com.example.javallmai.services;

import com.example.javallmai.mapper.MessageMapper;
import com.example.javallmai.models.Message;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final AiClient aiClient;

    public AiService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public Message generate(List<Message> messages) {
        Prompt prompt = new Prompt(MessageMapper.map(messages));
        AiResponse response = aiClient.generate(prompt);
        return MessageMapper.map(response.getGeneration());
    }
}

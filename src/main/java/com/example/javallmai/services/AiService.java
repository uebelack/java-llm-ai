package com.example.javallmai.services;

import com.example.javallmai.mapper.MessageMapper;
import com.example.javallmai.models.Message;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {

    private final AiClient aiClient;

    @Value("classpath:/prompts/system.st")
    private Resource systemResource;

    public AiService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public Message generate(List<Message> messages) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);

        List<org.springframework.ai.prompt.messages.Message> mappedMessages = new ArrayList<>();
        mappedMessages.add(systemPromptTemplate.createMessage());
        mappedMessages.addAll(MessageMapper.map(messages));

        Prompt prompt = new Prompt(mappedMessages);

        AiResponse response = aiClient.generate(prompt);
        return MessageMapper.map(response.getGeneration());
    }
}

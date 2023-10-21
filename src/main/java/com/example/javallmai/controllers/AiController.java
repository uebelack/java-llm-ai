package com.example.javallmai.controllers;

import com.example.javallmai.models.Message;
import com.example.javallmai.services.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public Message generate(@RequestBody List<Message> messages) {
        return aiService.generate(messages);
    }
}

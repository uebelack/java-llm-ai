package com.example.javallmai.models;

import org.springframework.ai.prompt.messages.MessageType;

public record Message(MessageType role, String content) {

}

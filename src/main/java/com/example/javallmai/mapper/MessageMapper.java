package com.example.javallmai.mapper;

import com.example.javallmai.models.Message;
import org.springframework.ai.client.Generation;
import org.springframework.ai.prompt.messages.*;

import java.util.List;

public class MessageMapper {

    public static List<org.springframework.ai.prompt.messages.Message> map(List<Message> messages) {
        return messages.stream()
                .map(message ->
                        (org.springframework.ai.prompt.messages.Message) switch (message.role()) {
                            case ASSISTANT -> new AssistantMessage(message.content());
                            case USER -> new UserMessage(message.content());
                            case SYSTEM -> new SystemMessage(message.content());
                            case FUNCTION -> new FunctionMessage(message.content());
                        })
                .toList();
    }

    public static Message map(Generation generation) {
        return new Message(MessageType.ASSISTANT, generation.getText());
    }
}

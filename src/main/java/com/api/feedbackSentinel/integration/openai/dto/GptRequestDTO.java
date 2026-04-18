package com.api.feedbackSentinel.integration.openai.dto;

import java.util.List;

public record GptRequestDTO(String model, List<MessageDTO> messages, Double temperature) {
}

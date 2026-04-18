package com.api.feedbackSentinel.integration.openai.dto;

import java.util.List;


public record GptResponseDTO(List<ChoiceDTO> choices){


}

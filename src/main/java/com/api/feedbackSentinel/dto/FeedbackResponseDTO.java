package com.api.feedbackSentinel.dto;

public record FeedbackResponseDTO(Long id, String textoOriginal, String sentimento, String categoria) {
}

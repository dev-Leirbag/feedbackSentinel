package com.api.feedbackSentinel.service;

import com.api.feedbackSentinel.dto.FeedbackRequestDTO;
import com.api.feedbackSentinel.dto.FeedbackResponseDTO;
import com.api.feedbackSentinel.model.Feedback;
import com.api.feedbackSentinel.repository.FeedbackRepository;
import com.api.feedbackSentinel.integration.openai.service.GptIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final GptIntegrationService gptIntegrationService;

    private final ObjectMapper objectMapper;

    public FeedbackResponseDTO processaFeedback(FeedbackRequestDTO requestDTO) {

        var retornoGpt = gptIntegrationService.analisarFeedback(requestDTO.texto());

        try{
            var objeto = objectMapper.readValue(retornoGpt.toString(), Feedback.class);

            Feedback feedback = new Feedback();
            feedback.setCategoria(objeto.getCategoria());
            feedback.setSentimento(objeto.getSentimento());
            feedback.setDataCriacao(LocalDateTime.now());
            feedback.setTextoOriginal(requestDTO.texto());

            var feedbackSalvo = feedbackRepository.save(feedback);

            return new FeedbackResponseDTO(
                    feedbackSalvo.getId(),
                    feedbackSalvo.getTextoOriginal(),
                    feedbackSalvo.getSentimento(),
                    feedbackSalvo.getCategoria()
            );

        }catch(Exception e){
            throw new RuntimeException("Falha ao tentar processar o JSON da IA: " + e.getMessage(), e);
        }

    }
}

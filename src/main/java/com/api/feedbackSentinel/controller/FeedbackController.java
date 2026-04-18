package com.api.feedbackSentinel.controller;

import com.api.feedbackSentinel.dto.FeedbackRequestDTO;
import com.api.feedbackSentinel.dto.FeedbackResponseDTO;
import com.api.feedbackSentinel.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedBackService;

    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackResponseDTO> criaFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO){
        var feedbackResponse = feedBackService.processaFeedback(feedbackRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackResponse);

    }

}

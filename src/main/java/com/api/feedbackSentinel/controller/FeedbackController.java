package com.api.feedbackSentinel.controller;

import com.api.feedbackSentinel.dto.FeedbackRequestDTO;
import com.api.feedbackSentinel.dto.FeedbackResponseDTO;
import com.api.feedbackSentinel.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sentinel")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedBackService;

    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackResponseDTO> criaFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO){
        var feedbackResponse = feedBackService.processaFeedback(feedbackRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackResponse);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FeedbackResponseDTO>> listaFeedback(){
        var listaFeedback = feedBackService.listaFeedback();
        return ResponseEntity.status(HttpStatus.OK).body(listaFeedback);

    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<FeedbackResponseDTO> listaFeedbackPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(feedBackService.listaFeedbackPorId(id));
    }
}

package com.api.feedbackSentinel.service;

import com.api.feedbackSentinel.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

}

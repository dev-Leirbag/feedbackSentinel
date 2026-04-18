package integration.openai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GptIntegrationService {

    private final WebClient webCliente;

    @Value("${API_KEY}")
    private String apiKey;
}

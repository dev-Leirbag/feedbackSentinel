package com.api.feedbackSentinel.integration.openai.service;

import com.api.feedbackSentinel.integration.openai.dto.GptRequestDTO;
import com.api.feedbackSentinel.integration.openai.dto.GptResponseDTO;
import com.api.feedbackSentinel.integration.openai.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GptIntegrationService {

    private final WebClient webCliente;

    @Value("${API_KEY}")
    private String apiKey;

    public String analisarFeedback(String textoDoUsuario) {

        //1. Mensagem do Sistema ( O nosso prompt)
        // Aqui damos a ordem estrita para ele retornar ESCLUSIVAMENTE um JSON
        String regrasDoSistema = """
                    Você é um analista de feedback. Analise o texto e classifique o sentimento (Positivo, Negativo e Neutro)
                    e a categoria (Bug, Elogio, Sugestao e Duvida).
                    Retorne EXCLUSIVAMENTE um JSON válido no formato: {"sentimento": "...", "categoria": "..."}
                    Nao adicione nenhuma outra palavra ou formataçaõ.
                """;
        var systemMessage = new MessageDTO("system", regrasDoSistema);

        //2. Mensagem do Usuario
        var userMessage = new MessageDTO("user", textoDoUsuario);

        //3. Montar o request usando o DTO
        GptRequestDTO requestDTO = new GptRequestDTO(
                "gpt-4o-mini",
                List.of(systemMessage, userMessage), //Passamos a lista de mensagens
                0.0 // Temperatura 0.0 para ele ser analitico e seguir as regras do sistema de forma rigorosa
        );

        try {
            // [NOVO] 1. Vamos criar um ObjectMapper aqui rapidinho
            ObjectMapper mapper = new ObjectMapper();

            // [NOVO] 2. Vamos transformar o nosso DTO em String manualmente!
            String jsonPayload = mapper.writeValueAsString(requestDTO);

            // [NOVO] 3. Vamos imprimir no console para termos certeza do que estamos mandando
            System.out.println("🚨 ENVIANDO PARA A OPENAI: " + jsonPayload);

            // 4. Executa a requisição enviando a String!
            GptResponseDTO response = webCliente.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON) // [NOVO] Obrigamos a dizer que é um JSON
                    .bodyValue(jsonPayload) // [NOVO] Passamos a nossa String manual aqui
                    .retrieve()
                    .bodyToMono(GptResponseDTO.class)
                    .block();

            return response.choices().get(0).message().content();

        } catch (WebClientResponseException e) {
            System.err.println("🚨 RESPOSTA DE ERRO DA OPENAI: " + e.getResponseBodyAsString());
            throw new RuntimeException("Erro na OpenAI: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Erro interno ao processar JSON: " + e.getMessage());
        }
    }

}

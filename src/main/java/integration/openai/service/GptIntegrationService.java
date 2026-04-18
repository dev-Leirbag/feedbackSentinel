package integration.openai.service;

import integration.openai.dto.GptRequestDTO;
import integration.openai.dto.GptResponseDTO;
import integration.openai.dto.MessageDTO;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptIntegrationService {

    private final WebClient webCliente;

    @Value("${API_KEY}")
    private String apiKey;

    public String analisarFeedback(String textoDoUsuario){

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

        //Executando a requisicao
        GptResponseDTO response = webCliente.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(GptResponseDTO.class)
                .block();

        return response.choices().get(0).message().content();
    }
}

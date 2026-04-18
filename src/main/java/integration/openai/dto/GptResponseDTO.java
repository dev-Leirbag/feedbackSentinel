package integration.openai.dto;

import java.util.List;

public class GptResponseDTO {
    List<ChoiceDTO> choices;


    public class ChoiceDTO {
        MessageDTO message;
    }

    public class MessageDTO {
        String message;
    }

}

package integration.openai.dto;

import java.util.List;


public record GptResponseDTO(List<ChoiceDTO> choices){


}

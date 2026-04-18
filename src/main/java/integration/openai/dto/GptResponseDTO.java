package integration.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GptResponseDTO {
    List<ChoiceDTO> choices;


    public class ChoiceDTO {
        MessageDTO message;
    }

}

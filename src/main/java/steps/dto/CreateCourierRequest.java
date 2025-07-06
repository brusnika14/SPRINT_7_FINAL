package steps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCourierRequest {
    private String login;
    private String password;
}

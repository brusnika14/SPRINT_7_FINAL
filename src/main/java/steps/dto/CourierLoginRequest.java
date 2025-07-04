package steps.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CourierLoginRequest {
    private String login;
    private String password;
}

package steps.dto;


import lombok.Data;

@Data
public class CourierLoginRequest {
    private String login;
    private String password;
}

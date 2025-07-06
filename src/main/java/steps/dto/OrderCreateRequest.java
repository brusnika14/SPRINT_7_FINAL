package steps.dto;


import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class OrderCreateRequest {
    private String firstName = "Зина";
    private String lastName = "Зиновей";
    private String address = "Новосибирск, ул. Ленина, д.5, кв.55";
    private int metroStation = 1;
    private String phone = "8 900 555 55 88";
    private String rentTime = "1";
    private String deliveryDate = "2025-05-05";
    private String comment = "Пожалуйста, доставьте самокат без поломок";
    private List<String> color;



        public OrderCreateRequest(List<String> color) {
            this.color = color != null ? color : Collections.emptyList();


        // геттеры и сеттеры
    }
}

package ru.prakticum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;
import steps.dto.OrderCreateRequest;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private int track;
    private final List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката - {0}")
    public static Object[][] dataGen() {
        return new Object[][]{
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()},
                {null}
        };
    }

    @Before
    public void setUp() {
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @After
    public void tearDown() {
        if (track != 0) {
            try {
                OrderSteps.deleteOrder(track)
                        .statusCode(200);
                System.out.println("Успешно удален заказ с track: " + track);
            } catch (Exception e) {
                System.out.println("Ошибка при удалении заказа: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с разными вариантами цветов самоката")
    public void orderCreate() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(color);
        ValidatableResponse response = new OrderSteps().orderCreate(orderCreateRequest);

        track = response.extract().path("track");

        response.assertThat()
                .statusCode(201)
                .body("track", notNullValue())
                .body("track", instanceOf(Integer.class));
    }
}
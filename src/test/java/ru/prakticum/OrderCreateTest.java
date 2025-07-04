package ru.prakticum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;
import steps.dto.OrderCreateRequest;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private static int track;

    @Parameterized.Parameter
    public List<String> color;

    @Parameterized.Parameters(name = "Цвет самоката - {0}")
    public static Object[][] dataGen() {
        return new Object[][]{
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()},
                {null}  // Добавляем тест с null
        };
    }
    @AfterClass
    public static void tearDown() {
        try {
            if (track != 0) {
                OrderSteps.deleteOrder(track)
                        .statusCode(200);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении заказа: " + e.getMessage());
        }
    }

    @BeforeClass
    public static void setup() {
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с разными вариантами цветов самоката")
    public void orderCreate() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(color);
        ValidatableResponse validatableResponse = new OrderSteps().orderCreate(orderCreateRequest);
        track = validatableResponse.extract().response().jsonPath().getInt("track");
        validatableResponse
                .log().ifValidationFails()
                .statusCode(201)
                .body("track", notNullValue())
                .body("track", instanceOf(Integer.class));
    }
}

package ru.prakticum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.*;

public class CourierCreateTest {
    private String login;
    private String password;
    private Integer courierId;

    @Before
    public void setUp() {
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        // Генерация уникальных тестовых данных для каждого теста
        login = "test_" + System.currentTimeMillis();
        password = "pass_" + System.currentTimeMillis();
    }

    @After
    public void tearDown() {
        // Удаляем курьера только если он был создан и мы знаем его ID
        if (courierId != null) {
            try {
                CourierSteps.deleteCourier(courierId)
                        .statusCode(200);
                System.out.println("Успешно удален курьер с ID: " + courierId);
            } catch (Exception e) {
                System.out.println("Ошибка при удалении курьера ID " + courierId + ": " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать с валидными данными")
    public void shouldReturnOkTrue() {
        CourierSteps.createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));

        // Получаем ID созданного курьера для последующего удаления
        courierId = CourierSteps.loginCourier(login, password)
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Попытка создать двух курьеров с одинаковым набором данных")
    public void createTwoIdenticalCouriers() {
        // Первое успешное создание
        CourierSteps.createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));

        // Получаем ID первого курьера
        courierId = CourierSteps.loginCourier(login, password)
                .statusCode(200)
                .extract()
                .path("id");

        // Попытка создать дубликат
        CourierSteps.createCourier(login, password)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без поля login")
    public void createCourierWithoutLogin() {
        // Не сохраняем ID, так как курьер не должен создаться
        CourierSteps.createCourier("", password)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без поля пароль")
    public void createCourierWithoutPassword() {
        // Не сохраняем ID, так как курьер не должен создаться
        CourierSteps.createCourier(login, "")
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
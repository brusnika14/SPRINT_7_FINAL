package ru.prakticum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;

public class CourierIdCreateTest {
    private String login;
    private String password;
    private int courierId;

    @Before
    public void setUp() {
        // Генерация тестовых данных
        login = "test_" + System.currentTimeMillis();
        password = "pass_" + System.currentTimeMillis();

        // Создание курьера перед каждым тестом
        CourierSteps.createCourier(login, password)
                .statusCode(201);

        // Получение ID курьера
        courierId = CourierSteps.loginCourier(login, password)
                .statusCode(200)
                .extract()
                .path("id");

        assertNotEquals("ID курьера не должен быть 0", 0, courierId);
    }

    @After
    public void tearDown() {
        // Удаление курьера после каждого теста
        if (courierId != 0) {
            try {
                CourierSteps.deleteCourier(courierId)
                        .statusCode(200);
            } catch (Exception e) {
                System.out.println("Ошибка при удалении курьера ID " + courierId + ": " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void shouldReturnId() {
        CourierSteps.loginCourier(login, password)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void loginCourierWithoutLogin() {
        CourierSteps.loginCourier("", password)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void loginCourierWithoutPassword() {
        CourierSteps.loginCourier(login, "")
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с несуществующим логином")
    public void loginCourierWithNonExistingLogin() {
        CourierSteps.loginCourier("nonexistent_" + login, password)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void loginCourierWithWrongPassword() {
        CourierSteps.loginCourier(login, "wrong_" + password)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
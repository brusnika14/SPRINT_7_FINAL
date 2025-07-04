package ru.prakticum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierIdCreateTest extends BaseCourierLoginTest {
    private static int courierId;

    @AfterClass
    public static void tearDown() {
        try {
            if (courierId != 0) {
                CourierSteps.deleteCourier(courierId)

                        .statusCode(200);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении курьера: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void shouldReturnId() {
        courierId = CourierSteps.loginCourier(login, password)

                .statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");

        Assert.assertNotEquals("ID курьера не должен быть 0", 0, courierId);
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
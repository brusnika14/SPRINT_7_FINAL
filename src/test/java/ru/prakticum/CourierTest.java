
package ru.prakticum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;


import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Test;
import steps.CourierSteps;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CourierTest {
    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать с валидными данными")
    public void shouldReturnOkTrue() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        CourierSteps
                .createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Попытка создать двух курьеров с одинаковым набором данных. Создание второго курьера должно провалиться")
    public void createTwoIdenticalCouriers() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        CourierSteps
                .createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));


        CourierSteps
                .createCourier(login,password)
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без поля login. Создание курьера не удалось")
    public void createCourierWithoutLogin() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        CourierSteps
                .createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));


        CourierSteps
                .createCourier("", password)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без поля пароль. Создание курьера не удалось")
    public void createCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        CourierSteps
                .createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));


        CourierSteps
                .createCourier(login, "")
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

}

package ru.prakticum;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import steps.CourierSteps;

import static org.hamcrest.Matchers.is;

public class BaseCourierLoginTest {
    public String login;
    public String password;
    @Before
    public void setup() {
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        login = "test_" + RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        CourierSteps
                .createCourier(login, password)
                .statusCode(201)
                .body("ok", is(true));
    }
}

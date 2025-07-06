package ru.prakticum;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import steps.CourierSteps;

public class BaseCourierLoginTest {
    protected String login;
    protected String password;
    protected int courierId;

    @Before
    public void setup() {
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        login = "test_" + RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        CourierSteps.createCourier(login, password)
                .statusCode(201);

        courierId = CourierSteps.loginCourier(login, password)
                .extract()
                .path("id");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            try {
                CourierSteps.deleteCourier(courierId)
                        .statusCode(200);
            } catch (Exception e) {
                System.out.println("Failed to delete courier: " + e.getMessage());
            }
        }
    }
}
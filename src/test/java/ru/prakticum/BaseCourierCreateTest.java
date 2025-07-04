package ru.prakticum;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;

public class BaseCourierCreateTest {
    public String login;
    public String password;
    @Before
    public void setup() {
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        login = "test_" + RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
    }
}

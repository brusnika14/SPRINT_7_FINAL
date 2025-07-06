package ru.prakticum;



import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;

public class BaseCourierCreateTest {
    protected String login;
    protected String password;

    @Before
    public void setup() {
        // Генерируем уникальные данные для каждого теста
        login = "test_" + RandomStringUtils.randomAlphanumeric(10);
        password = "pass_" + RandomStringUtils.randomAlphanumeric(10);
    }
}
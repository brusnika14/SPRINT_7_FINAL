package ru.prakticum;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import constans.Endpoint;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static constans.Endpoint.ORDER_GET_LIST;
import static org.hamcrest.CoreMatchers.notNullValue;


public class OrderGetListTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void orderGetList() {
        given().log().all()
                .baseUri(Endpoint.BASE_URL)
                .get(ORDER_GET_LIST)
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}


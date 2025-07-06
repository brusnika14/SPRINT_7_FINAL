package steps;

import constans.Endpoint;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import steps.dto.OrderCreateRequest;

import static constans.Endpoint.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static RequestSpecification requestSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Endpoint.BASE_URL);
    }
    @Step("Создание нового заказа")
    public ValidatableResponse orderCreate(OrderCreateRequest orderCreateRequest) {
        return requestSpecification()
                .body(orderCreateRequest)
                .post(ORDER_POST_CREATE)
                .then();
    }
    @Step("Удаление заказа")
    public static ValidatableResponse deleteOrder(int track) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .param("track", track)
                .when()
                .put(ORDER_CANCEL)
                .then();
    }
}

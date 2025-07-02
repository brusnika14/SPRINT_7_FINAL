package steps;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import constans.Endpoint;
import steps.dto.OrderCreateRequest;
import static io.restassured.RestAssured.given;
import static constans.Endpoint.ORDER_POST_CREATE;

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


}

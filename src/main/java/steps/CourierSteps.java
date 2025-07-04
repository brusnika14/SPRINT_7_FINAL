package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import steps.dto.CourierLoginRequest;
import steps.dto.CreateCourierRequest;

import static constans.Endpoint.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создание нового курьера")
    public static ValidatableResponse createCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(
                    new CreateCourierRequest(
                            login,
                            password
                    )
                )
                .when()
                .post(COURIER_POST_CREATE)
                .then();
    }
    @Step("Логин курьера")
    public static ValidatableResponse loginCourier(String login, String password) {

        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(
                    new CourierLoginRequest(
                            login,
                            password
                    )
                )
                .when()
                .post(COURIER_POST_LOGIN)
                .then();
    }
    @Step("Удаление курьера")
    public static ValidatableResponse deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .pathParam("id", id)
                .when()
                .delete(COURIER_DELETE)
                .then();
    }
}
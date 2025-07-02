package steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static constans.Endpoint.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    public static ValidatableResponse createCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(String.format("{\"login\":\"%s\",\"password\":\"%s\",\"firstName\":\"saske\"}",
                        login, password))
                .when()
                .post(COURIER_POST_CREATE)
                .then();
    }

    public static ValidatableResponse loginCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(String.format("{\"login\":\"%s\",\"password\":\"%s\"}",
                        login, password))
                .when()
                .post(COURIER_POST_LOGIN)
                .then();
    }

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
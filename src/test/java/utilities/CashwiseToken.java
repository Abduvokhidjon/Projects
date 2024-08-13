package utilities;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CashwiseToken {
    public static String getToken() {
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("jamolov121997@gmail.com");
        requestBody.setPassword("PLm9V5c2tQrqAgu");
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        String token = response.jsonPath().getString("jwt_token");
        return token;
    }
}

package api;

import entities.RequestBody;
import io.cucumber.java.it.Ma;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.CashwiseToken;
import utilities.Config;


import java.util.HashMap;
import java.util.Map;


public class TestAPI {


    @Test
    public void testToken() {
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("jamolov121997@gmail.com");
        requestBody.setPassword("PLm9V5c2tQrqAgu");

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
//
        String token = response.jsonPath().getString("jwt_token");
    }


    @Test
    public void getSingleSeller() {

        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers/" + 4610;
        String token = CashwiseToken.getToken();
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        response.prettyPrint();
        String expectedEmail = response.jsonPath().getString("email");
        Assert.assertNotNull(expectedEmail);
        Assert.assertTrue(expectedEmail.endsWith(".com"));
    }



    @Test
    public void getAllSellers() {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers";
       String token = CashwiseToken.getToken();
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("isArchived", false);
        queryParam.put("page", 1);
        queryParam.put("size", 10);

        Response response = RestAssured.given().auth().oauth2(token)
                .params(queryParam).get(url);

        Assert.assertEquals(200, response.statusCode());
        response.prettyPrint();

        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertNotNull(email);
        System.out.println(email);
    }

    @Test
    public void getAllSellers2() {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers";
        String token = CashwiseToken.getToken();
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 10);

        Response response = RestAssured.given().auth().oauth2(token)
                .params(params).get(url);

        response.prettyPrint();

    }
}

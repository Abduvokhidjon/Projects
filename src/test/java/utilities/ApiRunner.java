package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class ApiRunner {
    @Getter
    private static CustomResponse customResponse;

    public static void runGET(String path, int id) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path;
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is not a list response");
        }


    }


    public static void runGET(String path, Map<String, Object> params) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path;
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }

    public static void runPost(String path, RequestBody requestBody) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path;

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);
        System.out.println("status code: " + response.statusCode());


        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }


    }


    public static void runPost(String path, Map<String, Object> params) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path;

        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);

        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }


    public static void runDelete(String path) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path;
        Response response = RestAssured.given().auth().oauth2(token).delete(url);
        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }


    public static void runPut(String path, RequestBody requestBody, int id) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path + id;
        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).put(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }



}

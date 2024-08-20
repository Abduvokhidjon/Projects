package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;

@Data
public class ApiRunner {
    @Getter
    private static CustomResponse customResponse;

    public static void runGET(String path) {
        String token = CashwiseToken.getToken();
        String url = Config.getProperty("cashWiseBaseURL") + path;
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        } catch (JsonProcessingException e) {
            System.out.println("this is not a list response");
        }
    }

}

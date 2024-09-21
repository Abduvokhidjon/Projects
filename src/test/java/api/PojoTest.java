package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashwiseToken;
import utilities.Config;

import java.util.*;

public class PojoTest {

    @Test
    public void createCategory() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/categories";
        String token = CashwiseToken.getToken();
        Faker faker = new Faker();

        RequestBody requestBody = new RequestBody();
        requestBody.setCategory_title(faker.company().name());
        requestBody.setCategory_description(faker.company().profession());
        requestBody.setFlag(false);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        response.prettyPrint();

        Assert.assertEquals(201, response.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        String url2 = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/categories/";
        System.out.println(customResponse.getCategory_id());
        Response response1 = RestAssured.given().auth().oauth2(token).get(url2 + customResponse.getCategory_id());
        response1.prettyPrint();
        Assert.assertEquals(response1.jsonPath().getInt("category_id"), customResponse.getCategory_id());
    }


    @Test
    public void createSeller() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers";
        String token = CashwiseToken.getToken();
        Faker faker = new Faker();
        RequestBody requestBody = new RequestBody();
        List<Response> responses = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Integer [] array = new Integer[15];
        for(int i = 0; i < 15; i ++) {
            requestBody.setCompany_name(faker.company().name());
            requestBody.setSeller_name(faker.name().firstName());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
            requestBody.setAddress(faker.address().streetAddress());

            Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                    .body(requestBody).post(url);
            responses.add((response));

            CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            array[i] = customResponse.getSeller_id();
            ids.add(customResponse.getSeller_id());

        }

        System.out.println(Arrays.toString(array));
        System.out.println(ids);



        String url2 = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers/archive/unarchive";

        Response response1 = RestAssured.given().auth().oauth2(token).queryParam("sellersIdsForArchive", ids).queryParam("archive", true)
                        .post(url2);


    }

    @Test
    public void createSellerWithoutEmail() {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers";
        String token = CashwiseToken.getToken();
        Faker faker = new Faker();
        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().firstName());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());
        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);
       response.prettyPrint();
       Assert.assertEquals(201, response.getStatusCode());
    }

    @Test
    public void archiveSeller() {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashwiseToken.getToken();
        Faker faker = new Faker();
        RequestBody requestBody = new RequestBody();
        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", 550008);
        params.put("archive", true);

        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);
        response.prettyPrint();
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void archiveAllSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers";
        String token = CashwiseToken.getToken();
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("isArchived", false);
        queryParam.put("page", 1);
        queryParam.put("size", 1000);

        Response response = RestAssured.given().auth().oauth2(token)
                .params(queryParam).get(url);

        ObjectMapper mapper = new ObjectMapper();

        String urlToArchive = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers/archive/unarchive";

        CustomResponse responses = mapper.readValue(response.asString(), CustomResponse.class);
        System.out.println(responses.getResponses().size());
        Map<String, Object > paramToArchive = new HashMap<>();
        for(int i = 0; i < responses.getResponses().size(); i++) {
            String email = responses.getResponses().get(i).getEmail();
           if(email != null) {
               if (email.contains("@yahoo.com")) {
                   paramToArchive.put("sellersIdsForArchive", responses.getResponses().get(i).getSeller_id());
                   paramToArchive.put("archive", true);
                   Response response1 = RestAssured.given().auth().oauth2(token).params(paramToArchive).post(urlToArchive);
                   Assert.assertEquals(200, response1.statusCode());
               }
           }
        }
    }


    @Test
    public void getAllSellers () throws JsonProcessingException {
        String url = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers";
        String token = CashwiseToken.getToken();
        Faker faker = new Faker();
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().firstName());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setAddress(faker.address().streetAddress());

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);
//        response.prettyPrint();


        String url2 = Config.getProperty("cashWiseBaseURL") + "/api/myaccount/sellers/all";

    Response response1 = RestAssured.given().auth().oauth2(token).get(url2);


    ObjectMapper mapper = new ObjectMapper();

    CustomResponse []customResponse = mapper.readValue(response1.asString(), CustomResponse[].class);


        System.out.println(customResponse[0].getSeller_name());


    }


}

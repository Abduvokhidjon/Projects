package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.response.Response;
import lombok.Data;
import org.junit.Test;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private int category_id;
    private String category_title;
    private int seller_id;
    private String email;


//    private List<CustomResponse> responses;
    private CustomResponse[] responsesArray;
    private List<CustomResponse> responsesList;
}

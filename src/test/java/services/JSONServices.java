package services;

import io.restassured.http.ContentType;
import org.openqa.selenium.devtools.v135.network.model.Response;
import payloads.JSONPayload;
import testbase.TestBase;
import java.util.*;

import static io.restassured.RestAssured.given;

public class JSONServices extends TestBase {
    public static Response response;
    public static String requestBody;

    public String jsonRequest(String param1, String param2){
        requestBody = new JSONPayload().firstPayload(param1, param2);
        response = (Response) given().contentType(ContentType.JSON)
                .header("", "")
                .body(requestBody)
                .when().relaxedHTTPSValidation()
                .post("")
                .then().extract().response();
        return response.toString();
    }
}

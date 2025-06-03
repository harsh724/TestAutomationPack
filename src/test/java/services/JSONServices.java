package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;  // ✅ This is the one you want
import payloads.JSONPayload;
import testbase.TestBase;
import java.util.*;

import static io.restassured.RestAssured.given;

public class JSONServices extends TestBase {
    public static Response response;
    public static String requestBody;

    public String jsonRequest(String param1, String param2){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        requestBody = new JSONPayload().firstPayload(param1, param2);
        response = (Response) given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when().relaxedHTTPSValidation()
                .post("/posts")
                .then()
                .statusCode(201).extract().response();
        //System.out.println(response.asString());
        return response.asString();
    }
}

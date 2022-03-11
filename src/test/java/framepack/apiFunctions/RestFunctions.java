package framepack.apiFunctions;


import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import reports.ReportTrail;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;
import org.testng.SkipException;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class RestFunctions {

    private static RestFunctions instanceOfRestFunctions = null;

    public static RestFunctions getRestFunctionInstance() {
        if (instanceOfRestFunctions == null) {
            instanceOfRestFunctions = new RestFunctions();
        }
        return instanceOfRestFunctions;
    }

    public static Response get(String url) {
        ReportTrail.info("Get Request on url" + url);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
//                .header("Content-type", "application/json")
                .when()
                .get(url)
                .then()
                .extract().response();
        ReportTrail.info("Response: ==>> \n" + response.asString());
        return response;
    }

/*    public static Response get(String url,String authHeader) {
        ReportTrail.info("Get Request on url" + url);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization",authHeader)
                .when()
                .get(url)
                .then()
                .extract().response();
        ReportTrail.info("Response: ==>> \n" + response.asString());
        return response;
    }*/

    public static Response get(String url, Headers headers) {
        ReportTrail.info("Get Request on url" + url);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get(url)
                .then()
                .extract().response();
        ReportTrail.info("Response: ==>> \n" + response.asString());
        return response;
    }

    public static Response post(String url,String requestBody) {
        ReportTrail.info("Post Request on url" + url);
        ReportTrail.info(".. with the body as request body: \n" + requestBody);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();
        ReportTrail.info("Response: ==>> \n" + response.asString());
        return response;
    }

    public static Response post(String url, String requestBody, Headers headers) {
        ReportTrail.info("Post Request on url" + url);
        ReportTrail.info(".. with the body as request body: \n" + requestBody);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(headers)
                .and()
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();
        ReportTrail.info("Response: ==>> \n" + response.asString());
        return response;
    }

    public static Response put(String url) {
        ReportTrail.info("Put Request on url" + url);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .and()
                .when()
                .put(url)
                .then()
                .extract().response();
        ReportTrail.info("Response: ==>> \n" + response.asString());
        return response;
    }

    public static Response put(String url,String requestBody,Headers headers) {
        ReportTrail.info("Put Request on url" + url);
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(headers)
                .and()
                .body(requestBody)
                .when()
                .put(url)
                .then()
                .extract().response();

        return response;

    }


/*


    public Response post(RequestSpecification requestSpec, String APIUrl) {
        Response resp = given()
                .relaxedHTTPSValidation()
                .auth()
                .preemptive()
                .basic("", "")
                .spec(requestSpec)
                .when()
                .post(APIUrl);
        return resp;
    }

    public Response post(Header auth, RequestSpecification requestSpec, String APIUrl) {
        Response resp =
                given()
                        .auth()
                        .preemptive()
                        .basic("", "")
                        .header(auth)
                        .spec(requestSpec)
                        .when()
                        .post(APIUrl);
        return resp;
    }
    public Response get(Header auth, String APIUrl) {
        Response resp = given().auth().preemptive().basic("", "").header(auth).when().get(APIUrl);
        return resp;
    }

    public Response put(Header auth, RequestSpecification requestSpec, String APIUrl) {
        Response resp =
                given().auth().preemptive().basic("", "").header(auth).spec(requestSpec).when().put(APIUrl);
        return resp;
    }

    *//**
     * Get the response from the API
     *
     * @param type The type of the response
     * @param postURL The API URL
     * @param body The JSON body, provide as a string
     * @return The response
     *//*
    public Response getAPIResponse(String type, String postURL, String body) {
        Response response = null;
        RestAssuredConfig config =
                RestAssured.config()
                        .httpClient(
                                HttpClientConfig.httpClientConfig()
                                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 100000)
                                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 100000));

        type = type.toLowerCase(Locale.ENGLISH);
        try {
            RequestSpecification httpRequest =
                    RestAssured.given().config(config).contentType("application" + "/json");
            ReportTrail.info("API: " + postURL);
            httpRequest.body(body);
            switch (type) {
                case "post":
                    response = httpRequest.post(postURL);
                    break;
                case "get":
                    response = httpRequest.get(postURL);
                    break;
                case "put":
                    response = httpRequest.put(postURL);
                    break;
            }
            ReportTrail.info(response.asString());
        } catch (Exception e) {
            e.printStackTrace();
            ReportTrail.skip("Failure in response from API");
            ReportTrail.info(e.getMessage());
        }
        if (response == null) {
            ReportTrail.skip("No response received from API");
            throw new SkipException("No response received from API");
        }
        return response;
    }*/
}
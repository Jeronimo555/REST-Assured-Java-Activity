package com.globant.automation.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

public class RequestBuilder {

    private static final String APIKEY = "x-api-key";

    public static Response getRequest(String baseUrl, String path, String apikey) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        if(apikey != null) {
            requestSpecification.header(APIKEY, apikey);
        }

        return requestSpecification.get(path);
    }

    public static Response postRequest(String baseUrl, String path, Object body, String apikey) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .body(body)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        if(apikey != null) {
            requestSpecification.header(APIKEY, apikey);
        }

        return requestSpecification.post(path);
    }

    public static Response deleteRequest(String baseUrl, String path, Integer id, String apikey) {
        RequestSpecification requestSpecification = RestAssured.given()
                .pathParam("idUser", id)
                .baseUri(baseUrl)
                .basePath(path)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        if(apikey != null) {
            requestSpecification.header(APIKEY, apikey);
        }

        return requestSpecification.delete("/{idUser}");
    }
}

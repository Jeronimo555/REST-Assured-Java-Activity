package com.globant.automation.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private static final String APIKEY = "x-api-key";

    //This function is meant for requests that have no query parameters.
    public static Response getRequest(String baseUrl, String path, String apikey) {
        return getRequest(baseUrl,path,new HashMap<>(),apikey);
    }

    //Use this function whenever you have to make a query with params.
    public static Response getRequest(String baseUrl, String path, Map<String,?> queryParams, String apikey){
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .queryParams(queryParams)
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

    public static Response deleteRequest(String baseUrl, String path, String apikey) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        if(apikey != null) {
            requestSpecification.header(APIKEY, apikey);
        }

        return requestSpecification.delete(path);
    }
}

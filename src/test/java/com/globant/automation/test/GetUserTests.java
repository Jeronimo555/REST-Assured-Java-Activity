package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.GetUserResponseDTO;
import com.globant.automation.model.UserDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class GetUserTests extends TestRunner {

    @Test(testName = "Verify user is found after search - 1")
    public void userFoundTestAssertion1() {
        RestAssured.given()
                .baseUri(getBaseurl())
                .header("Content-Type", "application/json")
                .header("x-api-key", getApikey())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                    .get("/users/2")
                .then()
                    .body("data.id", equalTo(2))
                    .body("data.first_name", equalTo("Janet"))
                    .body("data.last_name", equalTo("Weaver"));
    }

    @Test(testName = "Verify user is found after search - 2")
    public void userFoundTestAssertion2() {
        Response response = RequestBuilder.getRequest(getBaseurl(), "/users/2", getApikey());
        JsonPath jsonPath = response.jsonPath();

        Integer id = jsonPath.getInt("data.id");
        String firstName = jsonPath.getString("data.first_name");
        String lastName = jsonPath.getString("data.last_name");

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertEquals(id, 2, "The id should match");
        assertEquals(firstName, "Janet", "The firstName should match");
        assertEquals(lastName, "Weaver", "The lastName should match");
    }

    @Test(testName = "Verify user is not found after search - 3")
    public void userFoundTestAssertion3() {
        Response response = RequestBuilder.getRequest(getBaseurl(), "/users/2", getApikey());
        GetUserResponseDTO userResponseDTO = response.as(GetUserResponseDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertEquals(userResponseDTO.getUser().getId(), 2, "The id should match");
        assertEquals(userResponseDTO.getUser().getFirstName(), "Janet", "The firstName should match");
        assertEquals(userResponseDTO.getUser().getLastName(), "Weaver", "The lastName should match");
    }

    @Test(testName = "Verify user is not found after search - 4")
    public void userFoundTestAssertion4() {



        Response response = RequestBuilder.getRequest(getBaseurl(), "/users/2", getApikey());
        GetUserResponseDTO userResponseDTO = response.as(GetUserResponseDTO.class);
        GetUserResponseDTO expectedUserResponseDTO = GetUserResponseDTO.builder()
                .user(UserDTO.builder()
                        .id(2)
                        .firstName("Janet")
                        .lastName("Weaver")
                        .email("janet.weaver@reqres.in")
                        .build())
                .build();

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertEquals(userResponseDTO, expectedUserResponseDTO, "The user should match");
    }
}

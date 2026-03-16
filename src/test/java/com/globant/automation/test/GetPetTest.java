package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.PetDTO;
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

public class GetPetTest extends TestRunner {

    @Test(testName = "Consult data of a specific pet")
    public void getPetstorePetTest() {
        // Define the pet to search (ensure this user exists in the Petstore)
        Integer pet_id = 10;
        String pet_name = "doggie";

        // The endpoint is /pet/{petId}
        Response response = RequestBuilder.getRequest(getBaseurl(), "/pet/" + pet_id, getApikey());

        //Deserialization.
        PetDTO userResponse = response.as(PetDTO.class);

        //We make a couple of assertions to make sure the response body aligns with our data.
        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertEquals(userResponse.getId(), pet_id.longValue(), "The id should match");
        assertEquals(userResponse.getName(), pet_name, "The name should match");

        System.out.println("Pet found: " + userResponse.getName());

    }
}

package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.CreateUserDTO;
import com.globant.automation.model.CreateUserResponseDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class PostUserTest extends TestRunner {

    @Test(testName = "Create a user")
    public void getPetstorePetTest() {

        //Create the user based on the params found in the API documentation
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .id(0)
                .username("Dovakhinslayer")
                .email("email@test.com")
                .password("1234")
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/user", createUserDTO, getApikey());
        CreateUserResponseDTO createUserResponseDTO = response.as(CreateUserResponseDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");

        System.out.println("User successfully created.");

    }





}

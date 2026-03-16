package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.CreateUserDTO;
import com.globant.automation.model.CreateUserResponseDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class PostUserTest extends TestRunner {
    private String lastUsernameCreated;


    @Test(testName = "Create a user")
    public void getPetstorePetTest() {

        String username = "Dovakhinslayer";

        //Create the user based on the params found in the API documentation
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .id(0)
                .username(username)
                .email("email@test.com")
                .password("1234")
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/user", createUserDTO, getApikey());
        CreateUserResponseDTO createUserResponseDTO = response.as(CreateUserResponseDTO.class);

        if(response.getStatusCode() == 200) {
            this.lastUsernameCreated = username;
        }

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");

        System.out.println("User successfully created.");

    }

    @AfterMethod(alwaysRun = true)
    public void cleanupUser() {
        String username_to_cleanup = this.lastUsernameCreated;

        if (username_to_cleanup != null){
            System.out.println("Cleaning up: Deleting user " + username_to_cleanup);

            // 3. Perform the DELETE request to the Petstore API
            Response response = RequestBuilder.deleteRequest(getBaseurl(), "/user/" + username_to_cleanup, getApikey());

            // Verify deletion was successful
            assertEquals(response.getStatusCode(), 200, "Cleanup failed: User was not deleted.");

        }
    }





}

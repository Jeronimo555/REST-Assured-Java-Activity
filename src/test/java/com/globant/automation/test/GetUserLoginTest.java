package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.CreateUserDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class GetUserLoginTest extends TestRunner {
    private String username;
    private String password = "password123";

    @BeforeMethod
    public void setupUser() {
        //Arrange: Create a unique user for this specific test
        this.username = "Dovakhinslayer";

        CreateUserDTO setupPayload = CreateUserDTO.builder()
                .id(1234) // Unique ID
                .username(this.username)
                .password(this.password)
                .email("login_test@example.com")
                .build();

        //POST the user to ensure they exist
        Response response = RequestBuilder.postRequest(getBaseurl(), "/user", setupPayload, getApikey());
        assertEquals(response.getStatusCode(), 200, "Setup failed: Could not create user for login test.");
    }

    @Test(testName = "2. Login to the system")
    public void loginUserTest() {
        //Set up query parameters for login
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", this.username);
        queryParams.put("password", this.password);

        //Perform the GET login request
        //We use the overloaded method since we're using params.
        Response response = RequestBuilder.getRequest(getBaseurl(), "/user/login", queryParams, getApikey());

        //Verify the login was successful
        assertEquals(response.getStatusCode(), 200, "Login failed.");

        // The Petstore returns a session message like "logged in user session:12345678"
        String message = response.jsonPath().getString("message");
        assertTrue(message.contains("logged in user session"), "Login session message missing.");

        System.out.println("Login successful for user: " + this.username);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        // Cleanup
        if (this.username != null) {
            RequestBuilder.deleteRequest(getBaseurl(), "/user/" + this.username, getApikey());
            this.username = null;
        }
    }

}

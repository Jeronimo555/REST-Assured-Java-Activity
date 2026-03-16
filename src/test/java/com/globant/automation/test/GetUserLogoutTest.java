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

public class GetUserLogoutTest extends TestRunner {
    private String username;
    private final String password = "password123";

    @BeforeMethod
    public void setupAndLogin() {
        this.username = "Dovakhinslater";

        //Create User
        CreateUserDTO setupPayload = CreateUserDTO.builder()
                .username(this.username)
                .password(this.password)
                .build();
        RequestBuilder.postRequest(getBaseurl(), "/user", setupPayload, getApikey());

        //Login User
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", this.username);
        queryParams.put("password", this.password);

        Response loginResponse = RequestBuilder.getRequest(getBaseurl(), "/user/login", queryParams, getApikey());
        assertEquals(loginResponse.getStatusCode(),200,"The status code does not match");
    }

    @Test(testName = "Logout from the system")
    public void logoutUserTest() {
        //Perform the GET logout request
        Response response = RequestBuilder.getRequest(getBaseurl(), "/user/logout", getApikey());

        // 4. Assert: Verify the logout was successful
        assertEquals(response.getStatusCode(), 200, "Logout failed.");

        // The Petstore returns an "ok" message for successful logout
        String message = response.jsonPath().getString("message");
        assertEquals(message, "ok", "Logout response message should be 'ok'");

        System.out.println("User " + this.username + " successfully logged out.");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        // Cleanup: Delete the user created during setup
        if (this.username != null) {
            RequestBuilder.deleteRequest(getBaseurl(), "/user/" + this.username, getApikey());
            this.username = null;
        }
    }

}

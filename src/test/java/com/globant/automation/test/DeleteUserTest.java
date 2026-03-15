package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DeleteUserTest extends TestRunner {

    @Test(testName = "Validate user deletion")
    public void deleteUserTest() {
        Response response = RequestBuilder.deleteRequest(getBaseurl(), "/users", 1, getApikey());
        assertEquals(response.getStatusCode(), 204, "The status code doesn't match.");
    }
}

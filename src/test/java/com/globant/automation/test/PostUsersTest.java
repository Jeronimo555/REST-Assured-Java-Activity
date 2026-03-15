package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.CreateUserDTO;
import com.globant.automation.model.CreateUserResponseDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class PostUsersTest extends TestRunner {

    @Test(testName = "Validate user creation")
    public void createUserTest() {
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .job("TEST AUTOMATION")
                .name("Diego")
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/users", createUserDTO, getApikey());
        CreateUserResponseDTO createUserResponseDTO = response.as(CreateUserResponseDTO.class);

        assertEquals(response.getStatusCode(), 201, "The status code doesn't match.");
        assertTrue(createUserResponseDTO.getId() > 0, "The id should be greater than 0");
        assertEquals(createUserResponseDTO.getJob(), createUserDTO.getJob(), "The job should match");
        assertEquals(createUserResponseDTO.getName(), createUserDTO.getName(), "The name should match");
        assertNotNull(createUserResponseDTO.getCreatedAt(), "the createdAt should not be null");
    }
}

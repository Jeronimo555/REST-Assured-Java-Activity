package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.PetDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetAvailablePetsTest extends TestRunner {

    @Test(testName = "List every pet with status=available")
    public void getAvailablePetsTest(){
        Map<String,String> query_params = new HashMap<>();
        query_params.put("status","available");

        Response response = RequestBuilder.getRequest(getBaseurl(), "/pet/findByStatus",query_params, getApikey());
        PetDTO[] petResponse = response.as(PetDTO[].class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertTrue(petResponse.length > 0, "The list of available pets is empty!");

        printPetNames(petResponse);
    }


    //Asked Gemini to create a static method for printing the pet names.
    public static void printPetNames(PetDTO[] pets) {
        System.out.println("========================================");
        System.out.println("       LISTING AVAILABLE PET NAMES      ");
        System.out.println("========================================");

        if (pets == null || pets.length == 0) {
            System.out.println("No pets found in the list.");
        } else {
            for (int i = 0; i < pets.length; i++) {
                String name = pets[i].getName();
                // Handle null names to keep the output clean
                String displayName = (name != null && !name.isEmpty()) ? name : "[No Name Provided]";
                System.out.println((i + 1) + ". " + displayName);
            }
        }
        System.out.println("========================================");
    }

}

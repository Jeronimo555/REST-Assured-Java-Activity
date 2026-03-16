package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.OrderDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class PostOrderTest extends TestRunner {

    @Test(testName = "Create Order")
    public void createOrderTest(){
        OrderDTO order_payload = OrderDTO.builder()
                .id(555L) // Unique Order ID
                .petId(10L) // The ID of the pet being bought
                .quantity(1)
                .status("placed")
                .complete(true)
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/store/order", order_payload, getApikey());
        OrderDTO order_response = response.as(OrderDTO.class);

        assertEquals(order_response.getId(), order_payload.getId());
        assertEquals(order_response.getPetId(), order_payload.getPetId());

        System.out.println("Order successfully placed for Pet ID: " + order_response.getPetId());

    }

}

package com.limj0009.receiptprocessor.ControllerTest;

import com.limj0009.receiptprocessor.Controller.ReceiptController;
import com.limj0009.receiptprocessor.Model.Receipt;
import com.limj0009.receiptprocessor.Service.ReceiptService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptController.class)
public class ReceiptControllerTest {

    @MockBean
    private ReceiptService receiptService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testValidGetPoints() throws Exception {
        String id = "1";
        when(receiptService.getPoints(id)).thenReturn(10);
        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(10));
    }

    @Test
    void testInvalidGetPoints() throws Exception {
        String id = "1";
        when(receiptService.getPoints(id)).thenReturn(null);
        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testValidProcessReceipt() throws Exception {
        String jsonReceipt = """
        {
          "retailer": "M&M Corner Market",
          "purchaseDate": "2022-03-20",
          "purchaseTime": "14:33",
          "items": [
            {
              "shortDescription": "Gatorade",
              "price": "2.25"
            },{
              "shortDescription": "Gatorade",
              "price": "2.25"
            },{
              "shortDescription": "Gatorade",
              "price": "2.25"
            },{
              "shortDescription": "Gatorade",
              "price": "2.25"
            }
          ],
          "total": "9.00"
        }
        """;
        String id = "1";
        when(receiptService.processReceipt(any(Receipt.class))).thenReturn(id);
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReceipt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testInvalidProcessReceipt() throws Exception {
        when(receiptService.getPoints("1")).thenThrow(new NumberFormatException());
        mockMvc.perform(get("/receipts/1/points"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid data"));
    }
}

package com.limj0009.receiptprocessor.ServiceTest;

import com.limj0009.receiptprocessor.Model.Item;
import com.limj0009.receiptprocessor.Model.Receipt;
import com.limj0009.receiptprocessor.Repository.ReceiptRepository;
import com.limj0009.receiptprocessor.Service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ReceiptServiceTest {
    private ReceiptRepository receiptRepository;
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        receiptRepository = mock(ReceiptRepository.class);
        receiptService = new ReceiptService(receiptRepository);
    }

    @Test
    void testProcessReceipt() {
        Receipt input = new Receipt();
        input.setTotal("1.00");
        String id = receiptService.processReceipt(input);
        verify(receiptRepository).saveReceipt(eq(id), eq(input));
    }

    @Test
    void testGetPoints() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        Item item1 = new Item();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice("6.49");
        Item item2 = new Item();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice("12.25");
        Item item3 = new Item();
        item3.setShortDescription("Knorr Creamy Chicken");
        item3.setPrice("1.26");
        Item item4 = new Item();
        item4.setShortDescription("Doritos Nacho Cheese");
        item4.setPrice("3.35");
        Item item5 = new Item();
        item5.setShortDescription("   Klarbrunn 12-PK 12 FL OZ  ");
        item5.setPrice("12.00");
        receipt.setItems(List.of(item1, item2, item3, item4, item5));
        receipt.setTotal("35.35");

        when(receiptRepository.getReceipt("1")).thenReturn(receipt);

        int points = receiptService.getPoints("1");
        int expectedPoints = 28;
        assertEquals(points, expectedPoints);
    }

}

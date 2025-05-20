package com.limj0009.receiptprocessor.RepositoryTest;

import com.limj0009.receiptprocessor.Model.Receipt;
import com.limj0009.receiptprocessor.Repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReceiptRepositoryTest {
    private ReceiptRepository receiptRepository;

    @BeforeEach
    void setUp() {
        receiptRepository = new ReceiptRepository();
    }

    @Test
    void testValidSaveAndGetReceipt() {
        Receipt input = new Receipt();
        input.setTotal("1.00");
        String id = "1";
        receiptRepository.saveReceipt(id, input);
        Receipt output = receiptRepository.getReceipt(id);
        assertEquals(input, output);
    }

    @Test
    void testInvalidGetReceipt() {
        Receipt output = receiptRepository.getReceipt("2");
        assertNull(output);
    }
}

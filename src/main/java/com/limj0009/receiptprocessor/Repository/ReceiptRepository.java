package com.limj0009.receiptprocessor.Repository;

import com.limj0009.receiptprocessor.Model.Receipt;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReceiptRepository {
    private Map<String, Receipt> hashmap = new HashMap<>();
    public void saveReceipt(String id, Receipt receipt) {
        hashmap.put(id, receipt);
    }
    public Receipt getReceipt(String id) {
        return hashmap.get(id);
    }
}

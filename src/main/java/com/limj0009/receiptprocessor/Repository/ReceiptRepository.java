package com.limj0009.receiptprocessor.Repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReceiptRepository {
    private Map<String, Float> hashmap = new HashMap<>();
    public void addToReceipt(String id, Float price) {
        hashmap.put(id, price);
    }
    public Float getTotalPrice(String id) {
        return hashmap.get(id);
    }
}

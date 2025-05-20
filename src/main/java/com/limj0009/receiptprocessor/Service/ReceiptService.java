package com.limj0009.receiptprocessor.Service;

import com.limj0009.receiptprocessor.Model.Item;
import com.limj0009.receiptprocessor.Model.Receipt;
import com.limj0009.receiptprocessor.Repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.UUID.randomUUID;
@Service
public class ReceiptService {
    private ReceiptRepository receiptRepository;
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public String processReceipt(Receipt receipt) {
        String id = randomUUID().toString();
        receiptRepository.saveReceipt(id, receipt);
        return id;
    }

    public Integer getPoints(String id) {
        return calculatePoints(receiptRepository.getReceipt(id));
    }

    public int calculatePoints(Receipt receipt) {
        int totalPoints = 0;
        String retailer = receipt.getRetailer();
        BigDecimal totalPrice;
        List<Item> items = receipt.getItems();
        LocalDate purchaseDate;
        LocalTime purchaseTime;
        try {
            totalPrice = new BigDecimal(receipt.getTotal());
            purchaseDate = LocalDate.parse(receipt.getPurchaseDate());
            purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid data");
        }

        //One point for every alphanumeric character in the retailer name.
        for (char c : retailer.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                totalPoints++;
            }
        }

        //50 points if the total is a round dollar amount with no cents.
        if (totalPrice.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            totalPoints += 50;
        }

        //25 points if the total is a multiple of 0.25.
        if (totalPrice.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            totalPoints += 25;
        }

        //5 points for every two items on the receipt.
        totalPoints += (items.size() / 2) * 5;

        // If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer.
        // The result is the number of points earned.
        for (Item item : items) {
            String itemDescription = item.getShortDescription();
            int trimmedLength = itemDescription.trim().length();
            if (trimmedLength % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                price = price.multiply(new BigDecimal("0.2"));
                totalPoints += Math.ceil(price.doubleValue());
            }
        }

        //6 points if the day in the purchase date is odd.
        if (purchaseDate.getDayOfMonth() % 2 == 1) {
            totalPoints += 6;
        }

        //10 points if the time of purchase is after 2:00pm and before 4:00pm.
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) {
            totalPoints += 10;
        }

        return totalPoints;
    }
}

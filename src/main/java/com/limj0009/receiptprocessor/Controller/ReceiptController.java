package com.limj0009.receiptprocessor.Controller;

import com.limj0009.receiptprocessor.Model.Receipt;
import com.limj0009.receiptprocessor.Service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private ReceiptService receiptService;
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(Map.of("points", points));
        }
    }
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id", id));
    }
}

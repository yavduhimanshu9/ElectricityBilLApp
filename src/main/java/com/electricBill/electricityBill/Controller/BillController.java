package com.electricBill.electricityBill.Controller;

import com.electricBill.electricityBill.Entity.Bill;
import com.electricBill.electricityBill.Repository.BillRepository;
import com.electricBill.electricityBill.service.serviceImp.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillRepository billRepository;
    @PostMapping("/Bill/{meterId}")
    public ResponseEntity<Bill> getBill(@PathVariable Long meterId) {
        Bill existingBill = billRepository.findByMeterInfo_MeterId(String.valueOf(meterId));
        if (existingBill != null) {
            return ResponseEntity.ok(existingBill);
        } else {
            Bill newBill = billService.generateBill(String.valueOf(meterId));
            billRepository.save(newBill);
            return ResponseEntity.ok(newBill);
        }
    }
}
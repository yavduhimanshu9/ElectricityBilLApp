package com.electricBill.electricityBill.Controller;

import com.electricBill.electricityBill.Entity.MetersDetails;
import com.electricBill.electricityBill.service.MeterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

@RestController
public class MeterDetailsController {
    @Autowired
    private MeterDetailsService meterDetailsService;

    @PostMapping("/meterDetails")
    public ResponseEntity<MetersDetails> createMeterDetails(@RequestBody MetersDetails metersDetails) {
        MetersDetails createdMetersDetails = meterDetailsService.createMeterDetails(metersDetails);
        return ResponseEntity.ok(createdMetersDetails);
    }
    @GetMapping("/bill/{month}")
    public ResponseEntity<Double> getTotalBillForMonth(@PathVariable String month) {
        Month monthEnum = Month.valueOf(month.toUpperCase());
        double totalBill = meterDetailsService.getTotalBillForMonth(monthEnum);
        return ResponseEntity.ok(totalBill);
    }
}
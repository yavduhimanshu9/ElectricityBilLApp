package com.electricBill.electricityBill.Controller;

import com.electricBill.electricityBill.Entity.Bill;
import com.electricBill.electricityBill.Entity.MetersInformation;
import com.electricBill.electricityBill.Entity.Payment;
import com.electricBill.electricityBill.Repository.BillRepository;
import com.electricBill.electricityBill.Repository.PaymentRepository;
import com.electricBill.electricityBill.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Map;


/**
 * This class is a controller that handles requests related to the administration of the electricity bill system.
 * It uses the AdminService to perform operations related to the administration.
 */
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    BillRepository billRepository;
    @Autowired
    PaymentRepository paymentRepository;
    /**
     * This method is used to add meter information.
     * It takes a MeterInfo1 object as input which contains the details of the meter to be added.
     * If the meter information is added successfully, it returns a ResponseEntity with HTTP status 200 (OK) and a success message.
     * If the meter ID already exists, it returns a ResponseEntity with HTTP status 409 (Conflict) and an error message.
     * If there is an exception during the process, it returns a ResponseEntity with HTTP status 500 (Internal Server Error) and an error message.
     *
     * @param metersInformation This is the meter information to be added. It is passed in the request body.
     * @return ResponseEntity<String> This returns a ResponseEntity with either a success message or an error message, depending on whether the operation was successful or not.
     * @exception DataIntegrityViolationException On input error when meter ID already exists.
     * @exception Exception On other input errors.
     * @see DataIntegrityViolationException
     * @see Exception
     */
    @PostMapping("/addMeterInfo")
    public ResponseEntity<String> addMeterInfo(@RequestBody MetersInformation metersInformation) {
        try {
            adminService.createMeterInfo(metersInformation, metersInformation.getUserId().toString(), metersInformation.getDate().toString());
            return ResponseEntity.ok("Meter info added successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Meter ID already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PutMapping("/updateMeterInfo")
    public ResponseEntity<String> updateMeterInfo(@RequestBody MetersInformation metersInformation) {
        try {
            adminService.updateMeterInfo(metersInformation);
            return ResponseEntity.ok("Meter info updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/increaseUnitConsumed")
    public ResponseEntity<String> increaseUnitConsumed(@RequestBody Map<String, String> meterData) {
        try {
            String meterId = meterData.get("meterId");
            Double unitsToAdd = Double.valueOf(meterData.get("unitsToAdd"));
            adminService.increaseUnitConsumed(meterId, unitsToAdd);
            return ResponseEntity.ok("Unit consumed increased successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
   /* @PostMapping("/payBill")
    public ResponseEntity<String> payBill(@RequestBody Payment payment) {
        try {
            adminService.payBill(payment);
            return ResponseEntity.ok("Bill paid successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }*/

    @PostMapping("/payBill")
    public ResponseEntity<String> payBill(@RequestBody Payment payment) {
        try {
            adminService.payBill(payment);
            Bill bill = billRepository.findById(payment.getBillId()).orElse(null);
            if (bill != null) {
                double remainingAmount = bill.getAmount();
//                bill.setAmount(remainingAmount);
//                billRepository.save(bill);
                if (remainingAmount <= 0) {
                    payment.setStatus("Paid");
                } else {
                    payment.setStatus("Pending");
                }
                payment.setPaymentDate(LocalDate.now());
                paymentRepository.save(payment);
            }
            return ResponseEntity.ok("Bill paid successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}

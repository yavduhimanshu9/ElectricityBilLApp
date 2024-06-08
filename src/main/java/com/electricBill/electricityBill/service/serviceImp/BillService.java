package com.electricBill.electricityBill.service.serviceImp;

import com.electricBill.electricityBill.Entity.Bill;
import com.electricBill.electricityBill.Entity.MetersInformation;
import com.electricBill.electricityBill.Repository.AddMeterInfoRepository;
import com.electricBill.electricityBill.Repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private AddMeterInfoRepository addMeterInfoRepository;

    public Bill generateBill(String meterId) {
        MetersInformation meterInfo = addMeterInfoRepository.findByMeterId(meterId);
        if (meterInfo == null) {
            throw new RuntimeException("Meter with id " + meterId + " not found");
        }
        Double amount = meterInfo.getMeterBill();
        Bill bill = new Bill();
        bill.setMeterInfo(meterInfo);
        bill.setMonth(LocalDate.now());
        bill.setAmount(amount);
        billRepository.save(bill);
        return bill;
    }

    public Bill getBill(Long id) {
        return billRepository.findById(id).orElse(null);
    }
}
package com.electricBill.electricityBill.service.serviceImp;

import com.electricBill.electricityBill.Entity.MetersInformation;
import com.electricBill.electricityBill.Repository.AddMeterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeterInfoService {
    @Autowired
    private AddMeterInfoRepository addMeterInfoRepository;

    public List<MetersInformation> getMeterInfoForMonth(LocalDate month) {
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());
        return addMeterInfoRepository.findByDateBetween(startDate, endDate);
    }
    public MetersInformation getMeterInfoByMeterId(String meterId) {
        return addMeterInfoRepository.findByMeterId(meterId);
    }
}
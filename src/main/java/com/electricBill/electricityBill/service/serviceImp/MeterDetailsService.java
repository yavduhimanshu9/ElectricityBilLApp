package com.electricBill.electricityBill.service;

import com.electricBill.electricityBill.Entity.MeterType;
import com.electricBill.electricityBill.Entity.MetersDetails;
import com.electricBill.electricityBill.Entity.UserInformation;
import com.electricBill.electricityBill.Repository.MeterDetailsRepository;
import com.electricBill.electricityBill.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public class MeterDetailsService {
    @Autowired
    private MeterDetailsRepository meterDetailsRepository;
    @Autowired
    private UserInfoRepository userInformationRepository;

    public MetersDetails createMeterDetails(MetersDetails metersDetails) {
        double unitConsumed = metersDetails.getUnitConsumed();
        double bill = 0;
        double gst = 0;

        if (unitConsumed > 100) {


            if (metersDetails.getMeterType() == MeterType.COMMERCIAL) {
                bill = (unitConsumed - 100) * 10.5*2;

            } else if (metersDetails.getMeterType() == MeterType.DOMESTIC) {
                bill = (unitConsumed - 100) * 10.5;
            }
            gst = bill * 0.18;
        }

        metersDetails.setBill(bill);
        metersDetails.setGst(gst);
        metersDetails.setTotalBill(bill + gst);


        UserInformation userInformation = userInformationRepository.findById(metersDetails.getUserInformation().getId()).orElse(null);
        if (userInformation != null) {
            metersDetails.setUserInformation(userInformation);
        }

        return meterDetailsRepository.save(metersDetails);
    }
    public double getTotalBillForMonth(Month month) {
        List<MetersDetails> metersDetailsList = meterDetailsRepository.findByMonth(month);
        return metersDetailsList.stream().mapToDouble(MetersDetails::getTotalBill).sum();
    }
}
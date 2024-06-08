package com.electricBill.electricityBill.service;


import com.electricBill.electricityBill.Entity.MetersInformation;
import com.electricBill.electricityBill.Entity.Payment;
import com.electricBill.electricityBill.Entity.UserInformation;

public interface AdminService {
    public String createUser(UserInformation user);
    public UserInformation authenticateUser(String email, String password);
    //public AddMeterInfo createMeterInfo(AddMeterInfo  meterInfo);

    UserInformation getUserInfo(Long id);

  //  MetersInformation createMeterInfo(MetersInformation metersInformation, Long userId);


    MetersInformation createMeterInfo(MetersInformation metersInformation, String userId,String date);

    String getMeterBills(String id);

    void payBill(Payment payment);
    void updateMeterInfo(MetersInformation metersInformation);
    void increaseUnitConsumed(String meterId, Double unitsToAdd);
}
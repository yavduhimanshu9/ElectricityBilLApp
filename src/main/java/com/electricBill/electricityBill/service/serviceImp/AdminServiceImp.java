package com.electricBill.electricityBill.service.serviceImp;

import com.electricBill.electricityBill.Entity.*;
import com.electricBill.electricityBill.Repository.BillRepository;
import com.electricBill.electricityBill.Repository.PaymentRepository;
import com.electricBill.electricityBill.Repository.UserInfoRepository;
import com.electricBill.electricityBill.Repository.AddMeterInfoRepository;
import com.electricBill.electricityBill.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class implements the AdminService interface.
 * It provides the functionality for user creation, user authentication, user information retrieval, meter information creation, and meter bill retrieval.
 */
@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AddMeterInfoRepository addMeterInfoRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BillRepository billRepository;

    /**
     * This method is used to create a new user.
     * It takes a UserInfo object as input which contains the details of the user to be created.
     * The user is saved in the UserInfoRepository.
     *
     * @param user This is the user information to be created.
     */
    @Override
    public String createUser(UserInformation user) {
        UserInformation existingUser = userInfoRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "Email already in use";
        }
        userInfoRepository.save(user);
        return "User created successfully";
    }

    /**
     * This method is used to authenticate a user.
     * It takes an email and password as input.
     * If the user is authenticated successfully, it returns the user information.
     * If the user is not authenticated, it returns null.
     *
     * @param email    This is the email of the user to be authenticated.
     * @param password This is the password of the user to be authenticated.
     * @return UserInfo This returns the user information if the user is authenticated successfully, or null otherwise.
     */
    @Override
    public UserInformation authenticateUser(String email, String password) {
        UserInformation user = userInfoRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * This method is used to get the user information by their ID.
     * It takes a String object as input which is the ID of the user.
     * If the user is found, it returns the user information.
     * If the user is not found, it returns null.
     *
     * @param id This is the ID of the user.
     * @return UserInfo This returns the user information if the user is found, or null otherwise.
     */
    @Override
    public UserInformation getUserInfo(Long id) {
        Optional<UserInformation> optionalUserInfo = userInfoRepository.findById(id);
        return optionalUserInfo.orElse(null);
    }

    /**
     * This method is used to create meter information.
     * It takes a MeterInfo1 object and a user ID as input.
     * The MeterInfo1 object contains the details of the meter to be created.
     * The user ID is used to associate the meter information with a user.
     * The meter information is saved in the AddMeterInfoRepository.
     *
     * @param metersInformation This is the meter information to be created.
     * @param userId            This is the ID of the user to associate the meter information with.
     * @return MeterInfo1 This returns the created meter information.
     * /*
     */
    @Override
    public MetersInformation createMeterInfo(MetersInformation metersInformation, String userId, String date) {
        UserInformation userInformation = userInfoRepository.findById(Long.valueOf(userId)).orElse(null);
        if (userInformation != null) {
            List<MetersInformation> existingMetersInformation = userInformation.getMetersInformationList();
            for (MetersInformation existingMeterInfo : existingMetersInformation) {
                if (existingMeterInfo.getMeterId().equals(metersInformation.getMeterId())) {
                    throw new DataIntegrityViolationException("User ID is already used for this meter ID");
                }
            }
        }
        metersInformation.setUserInformation(userInformation);
        metersInformation.setDate(LocalDate.parse(date));
        addMeterInfoRepository.save(metersInformation);
        return metersInformation;
    }
    /*@Override
    public MetersInformation createMeterInfo(MetersInformation metersInformation, String userId, String date) {
        UserInformation userInformation = userInfoRepository.findById(Long.valueOf(userId)).orElse(null);
        if (userInformation != null) {
            List<MetersInformation> existingMetersInformation = userInformation.getMetersInformationList();
            for (MetersInformation existingMeterInfo : existingMetersInformation) {
                if (existingMeterInfo.getMeterId().equals(metersInformation.getMeterId())) {
                    throw new DataIntegrityViolationException("User ID is already used for this meter ID");
                }
            }
        }
        metersInformation.setUserInformation(userInformation);
        metersInformation.setDate(LocalDate.parse(date));
        addMeterInfoRepository.save(metersInformation);
        return metersInformation;
    }*/

    /**
     * This method is used to get the meter bills of a user by their ID.
     * It takes a String object as input which is the ID of the user.
     * If the user is found, it returns the meter bills.
     * If the user is not found, it returns null.
     *
     * @param id This is the ID of the user.
     * @return String This returns the meter bills if the user is found, or null otherwise.
     */
    @Override
    public String getMeterBills(String id) {
        UserInformation userInformation = userInfoRepository.findById(Long.valueOf(id)).orElse(null);
        if (userInformation != null) {
            List<MetersInformation> metersInformationList = userInformation.getMetersInformationList();
            double totalAmount = 0;
            for (MetersInformation metersInformation : metersInformationList) {
                double unitConsumed = metersInformation.getUnitConsumed();
                totalAmount += metersInformation.getMeterBill();
            }
            return "Total Amount to pay is: " + totalAmount;
        }
        return null;
    }


    @Override
    public void payBill(Payment payment) {
        paymentRepository.save(payment);
        Bill bill = billRepository.findById(payment.getBillId()).orElse(null);
        if (bill != null) {
            double remainingAmount = bill.getAmount() - payment.getAmountPaid();
            bill.setAmount(remainingAmount);
            billRepository.save(bill);
            MetersInformation metersInformation = addMeterInfoRepository.findByMeterId(bill.getMeterInfo().getMeterId());
            if (metersInformation != null) {
                double unitsPaid;
                if (metersInformation.getMeterType() == MeterType.COMMERCIAL) {
                    unitsPaid = payment.getAmountPaid() / 10;
                } else {
                    unitsPaid = payment.getAmountPaid() / 5;
                }
                double remainingUnits = metersInformation.getUnitConsumed() - unitsPaid;
                metersInformation.setUnitConsumed(remainingUnits);
                addMeterInfoRepository.save(metersInformation);
            }
        }
    }
    @Override
    public void updateMeterInfo(MetersInformation metersInformation) {
        MetersInformation existingMeterInfo = addMeterInfoRepository.findByMeterId(metersInformation.getMeterId());
        if (existingMeterInfo != null) {
            existingMeterInfo.setMeterType(metersInformation.getMeterType());
            existingMeterInfo.setUnitConsumed(metersInformation.getUnitConsumed());
            addMeterInfoRepository.save(existingMeterInfo);
        } else {
            throw new RuntimeException("Meter with id " + metersInformation.getMeterId() + " not found");
        }
    }
    @Override
    public void increaseUnitConsumed(String meterId, Double unitsToAdd) {
        MetersInformation existingMeterInfo = addMeterInfoRepository.findByMeterId(meterId);
        if (existingMeterInfo != null) {
            double newUnitConsumed = existingMeterInfo.getUnitConsumed() + unitsToAdd;
            existingMeterInfo.setUnitConsumed(newUnitConsumed);
            addMeterInfoRepository.save(existingMeterInfo);
        } else {
            throw new RuntimeException("Meter with id " + meterId + " not found");
        }
    }
}

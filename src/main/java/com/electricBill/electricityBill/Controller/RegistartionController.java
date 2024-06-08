package com.electricBill.electricityBill.Controller;

import com.electricBill.electricityBill.Entity.MetersInformation;
import com.electricBill.electricityBill.Entity.UserInformation;
import com.electricBill.electricityBill.service.AdminService;
import com.electricBill.electricityBill.service.serviceImp.MeterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * This class is a controller that handles requests related to the user operations of the electricity bill system.
 * It uses the AdminService to perform operations related to the user.
 */
@Controller
public class RegistartionController {

    @Autowired
    AdminService adminService;
    @Autowired
    private MeterInfoService meterInfoService;

    /**
     * This method is used to register a new user.
     * It takes a UserInfo object as input which contains the details of the user to be registered.
     * If the user is registered successfully, it returns a ResponseEntity with HTTP status 200 (OK) and a success message.
     * If there is an exception during the process, it returns a ResponseEntity with HTTP status 500 (Internal Server Error) and an error message.
     *
     * @param user This is the user information to be registered. It is passed in the request body.
     * @return ResponseEntity<String> This returns a ResponseEntity with either a success message or an error message, depending on whether the operation was successful or not.
     * @exception Exception On input error.
     * @see Exception
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInformation user) {
        try {
            String output=adminService.createUser(user); // The ID will be generated automatically when saving
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    /**
     * This method is used to authenticate a user.
     * It takes a Map object as input which contains the email and password of the user to be authenticated.
     * If the user is authenticated successfully, it returns a ResponseEntity with HTTP status 200 (OK) and the user information.
     * If the user is not authenticated, it returns a ResponseEntity with HTTP status 401 (Unauthorized) and null.
     *
     * @param credentials This is the email and password of the user to be authenticated. It is passed in the request body.
     * @return ResponseEntity<UserInfo> This returns a ResponseEntity with either the user information or null, depending on whether the operation was successful or not.
     */
    @PostMapping("/login")
    public ResponseEntity<UserInformation> loginUser(@RequestBody Map<String, String> credentials) {
        UserInformation user = adminService.authenticateUser(credentials.get("email"), credentials.get("password"));
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * This method is used to get the meter information of a user by their ID.
     * It takes a String object as input which is the ID of the user.
     * If the user is found, it returns a ResponseEntity with HTTP status 200 (OK) and the meter information.
     * If the user is not found, it returns a ResponseEntity with HTTP status 404 (Not Found) and null.
     *
     * @param id This is the ID of the user. It is passed in the path variable.
     * @return ResponseEntity<List<MeterInfo1>> This returns a ResponseEntity with either the meter information or null, depending on whether the operation was successful or not.
     */
//    @GetMapping("/userInfo/{id}/meterInfo")
//    public ResponseEntity<List<MetersInformation>> getMeterInfoByUserId(@PathVariable String id) {
//        UserInformation userInformation = adminService.getUserInfo(Long.valueOf(id));
//        if (userInformation != null) {
//            List<MetersInformation> metersInformationList = userInformation.getMetersInformationList();
//            return ResponseEntity.ok(metersInformationList);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
    @GetMapping("/meterInfo/{meterId}")
    public ResponseEntity<MetersInformation> getMeterInfoByMeterId(@PathVariable String meterId) {
        MetersInformation metersInformation = meterInfoService.getMeterInfoByMeterId(meterId);
        if (metersInformation != null) {
            return ResponseEntity.ok(metersInformation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * This method is used to get the meter bills of a user by their ID.
     * It takes a String object as input which is the ID of the user.
     * If the user is found, it returns a ResponseEntity with HTTP status 200 (OK) and the meter bills.
     * If the user is not found, it returns a ResponseEntity with HTTP status 404 (Not Found) and null.
     *
     * @param id This is the ID of the user. It is passed in the path variable.
     * @return ResponseEntity<String> This returns a ResponseEntity with either the meter bills or null, depending on whether the operation was successful or not.
     */
    @GetMapping("/user/{id}/bills")
    public ResponseEntity<String> getMeterBills(@PathVariable String id) {
        String  meterBills = adminService.getMeterBills(id);
        if (meterBills != null) {
            return ResponseEntity.ok(meterBills);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
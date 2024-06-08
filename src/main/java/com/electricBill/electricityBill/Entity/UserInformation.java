package com.electricBill.electricityBill.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


/**
 * This class represents the User Information entity.
 * It includes details such as the user ID, name, email, and password.
 * It also includes a list of MeterInfo1 objects associated with the user.
 */
@Entity
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;

    @Column(unique=true)
    String email;

    String password;
    /**
     * Default constructor for UserInfo.
     */
    public UserInformation() {
    }
    /**
     * This constructor is used to create a new UserInfo object.
     *
     * @param name This is the name of the user.
     * @param email This is the email of the user.
     * @param password This is the password of the user.
     */
    public UserInformation(String name, String email, String password) {


        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * This method is used to get the list of MeterInfo1 objects associated with the user.
     *
     * @return List<MeterInfo1> This returns the list of MeterInfo1 objects.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "userInformation",cascade = CascadeType.ALL)
    List<MetersInformation> metersInformationList;

    public List<MetersInformation> getMetersInformationList() {
        return metersInformationList;
    }
    /**
     * This method is used to set the list of MeterInfo1 objects associated with the user.
     *
     * @param metersInformationList This is the list of MeterInfo1 objects to be set.
     */

    public void setMetersInformationList(List<MetersInformation> metersInformationList) {
        this.metersInformationList = metersInformationList;
    }
    @OneToMany(mappedBy = "userInformation", cascade = CascadeType.ALL)
    private List<MetersDetails> metersDetails;
}
package com.electricBill.electricityBill.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class represents the Meter Information entity.
 * It includes details such as the meter ID, meter type, and units consumed.
 * It also includes a method to calculate the meter bill based on the meter type and units consumed.
 */
@Entity
@Table(name = "meters_information")
public class MetersInformation {
    @Id
@Column(name = "meter_id")
    String meterId;
    @JsonDeserialize(using = MeterTypeDeserializer.class)
    MeterType meterType;
    @Column(name = "meter_date")
    private LocalDate date;
    Double unitConsumed;



    public MetersInformation() {
    }
    /**
     * This constructor is used to create a new MeterInfo1 object.
     *

     * @param meterId This is the ID of the meter.
     * @param meterType This is the type of the meter.
     * @param unitConsumed This is the number of units consumed.
     */
    public MetersInformation(String meterId, MeterType meterType, Double unitConsumed) {

        this.meterId = meterId;
        this.meterType = meterType;

        this.unitConsumed = unitConsumed;
    }



    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }


    public Double getUnitConsumed() {
        return unitConsumed;
    }

    public void setUnitConsumed(Double unitConsumed) {
        this.unitConsumed = unitConsumed;
    }
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserInformation userInformation;
    @Transient
    private Long userId;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }


    /**
     * This method is used to calculate the meter bill.
     * If the meter type is COMMERCIAL, the bill is calculated as the units consumed multiplied by 10.
     * If the meter type is not COMMERCIAL, the bill is calculated as the units consumed multiplied by 5.
     *
     * @return Double This returns the calculated meter bill.
     */

    public Double getMeterBill() {
        if (meterType == MeterType.COMMERCIAL) {
            return unitConsumed * 10;
        } else {
            return unitConsumed * 5;
        }
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    @OneToMany(mappedBy = "meterInfo",cascade = CascadeType.ALL)
    List<Bill> billList;
    public void setDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatter);
    }


}

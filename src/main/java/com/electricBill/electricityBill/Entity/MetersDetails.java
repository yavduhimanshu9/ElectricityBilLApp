package com.electricBill.electricityBill.Entity;

import jakarta.persistence.*;

import java.time.Month;

@Entity
public class MetersDetails {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInformation userInformation;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "meter_id")
    private String meterId;

    public Month getMonth() {
        return month;
    }
    @Column(name = "meter_type")
    @Enumerated(EnumType.STRING)
    private MeterType meterType;

    @Column(name = "month")
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(name = "unit_consumed")
    private Double unitConsumed;

    @Column(name = "bill")
    private Double bill;

    @Column(name = "gst")
    private Double gst;

    @Column(name = "total_bill")
    private Double totalBill;

    public void setMonth(Month month) {
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Double totalBill) {
        this.totalBill = totalBill;
    }
    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

}
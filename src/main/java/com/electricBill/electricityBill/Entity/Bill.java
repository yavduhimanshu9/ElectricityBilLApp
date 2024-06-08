package com.electricBill.electricityBill.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meter_id", referencedColumnName = "meter_id")
    private MetersInformation meterInfo;



    private Double amount;
    private LocalDate month;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetersInformation getMeterInfo() {
        return meterInfo;
    }

    public void setMeterInfo(MetersInformation meterInfo) {
        this.meterInfo = meterInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }


}
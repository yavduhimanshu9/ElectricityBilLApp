package com.electricBill.electricityBill.Repository;

import com.electricBill.electricityBill.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findByMeterInfo_MeterId(String meterId);

}
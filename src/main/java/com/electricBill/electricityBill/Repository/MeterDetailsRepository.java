package com.electricBill.electricityBill.Repository;

import com.electricBill.electricityBill.Entity.MetersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.util.List;

public interface MeterDetailsRepository extends JpaRepository<MetersDetails, Long> {
    @Query("SELECT m FROM MetersDetails m WHERE m.month = :month")
    List<MetersDetails> findByMonth(@Param("month") Month month);
}
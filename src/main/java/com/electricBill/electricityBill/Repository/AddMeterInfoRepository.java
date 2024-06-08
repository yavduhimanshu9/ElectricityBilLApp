package com.electricBill.electricityBill.Repository;

import com.electricBill.electricityBill.Entity.MetersInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AddMeterInfoRepository extends JpaRepository<MetersInformation, String>{
    List<MetersInformation> findByDateBetween(LocalDate startDate, LocalDate endDate);
    MetersInformation findByMeterId(String meterId);
}
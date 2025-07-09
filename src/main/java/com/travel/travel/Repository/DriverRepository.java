package com.travel.travel.Repository;

import com.travel.travel.Models.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Long> {

    Page<Driver> findAll(Pageable pageable);//Future pageable wll apply page=0,size=5

    List<Driver> findByAgency_Id(Long agencyId);

    Driver findByUser_Id(Long userId);
}

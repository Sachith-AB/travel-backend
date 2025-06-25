package com.travel.travel.Repository;

import com.travel.travel.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Long> {

}

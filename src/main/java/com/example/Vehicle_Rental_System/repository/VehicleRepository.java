package com.example.Vehicle_Rental_System.repository;

import com.example.Vehicle_Rental_System.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByAvailabilityStatusTrue();
}
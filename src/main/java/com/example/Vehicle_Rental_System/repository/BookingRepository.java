package com.example.Vehicle_Rental_System.repository;

import com.example.Vehicle_Rental_System.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByVehicleId(Long vehicleId);

    @Query("SELECT b FROM Booking b WHERE b.vehicle.id = :vehicleId " +
            "AND b.status = 'ACTIVE' " +
            "AND (b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Booking> findOverlappingBookings(
            @Param("vehicleId") Long vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
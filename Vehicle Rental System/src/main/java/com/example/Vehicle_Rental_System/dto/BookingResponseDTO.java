package com.example.Vehicle_Rental_System.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long vehicleId;
    private String vehicleBrand;
    private String vehicleModel;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private Double totalAmount;
    private String status;
}
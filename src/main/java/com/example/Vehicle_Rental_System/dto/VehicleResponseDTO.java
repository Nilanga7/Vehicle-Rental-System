package com.example.Vehicle_Rental_System.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponseDTO {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String registrationNumber;
    private Double dailyRate;
    private Boolean availabilityStatus;
}
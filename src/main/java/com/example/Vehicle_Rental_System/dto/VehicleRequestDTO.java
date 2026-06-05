package com.example.Vehicle_Rental_System.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDTO {

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "Vehicle type cannot be blank")
    private String type;

    @NotBlank(message = "Registration Number cannot be blank")
    private String registrationNumber;

    @NotNull(message = "Daily rate cannot be null")
    @Positive(message = "Daily rate must be greater than 0")
    private Double dailyRate;

    private Boolean availabilityStatus = true;
}
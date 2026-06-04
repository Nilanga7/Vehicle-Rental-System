package com.example.Vehicle_Rental_System.dto;

import com.example.Vehicle_Rental_System.config.ValidDateRange;
import com.example.Vehicle_Rental_System.entity.Vehicle;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidDateRange
public class BookingRequestDTO {

    @NotNull(message = "Customer ID cannot be null")
    @Positive(message = "Customer ID must be positive")
    private Long customerId;

    @NotNull(message = "Vehicle ID cannot be null")
    @Positive(message = "Vehicle ID must be positive")
    private Long vehicleId;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
}
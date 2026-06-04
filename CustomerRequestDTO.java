package com.example.Vehicle_Rental_System.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone must contain exactly 10 digits")
    private String phone;

    @NotBlank(message = "License number cannot be blank")
    @Size(min = 5, max = 20, message = "License number must be 5-20 characters")
    private String licenseNumber;
}

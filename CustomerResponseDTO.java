package com.example.Vehicle_Rental_System.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String licenseNumber;

}

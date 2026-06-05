package com.example.Vehicle_Rental_System.config;

import com.example.Vehicle_Rental_System.dto.BookingRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator
        implements ConstraintValidator<ValidDateRange, BookingRequestDTO> {

    @Override
    public boolean isValid(BookingRequestDTO dto,
                           ConstraintValidatorContext context) {

        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            return true;
        }

        return dto.getEndDate().isAfter(dto.getStartDate());
    }
}
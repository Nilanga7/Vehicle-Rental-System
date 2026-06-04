package com.example.Vehicle_Rental_System.service;

import com.example.Vehicle_Rental_System.dto.BookingRequestDTO;
import com.example.Vehicle_Rental_System.dto.BookingResponseDTO;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO dto);
    List<BookingResponseDTO> getAllBookings();
    BookingResponseDTO getBookingById(Long id);
    BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto);
    void deleteBooking(Long id);
    List<BookingResponseDTO> getBookingsByCustomer(Long customerId);
    List<BookingResponseDTO> getBookingsByVehicle(Long vehicleId);
    BookingResponseDTO cancelBooking(Long id);
}
package com.example.Vehicle_Rental_System.service.impl;

import com.example.Vehicle_Rental_System.dto.BookingRequestDTO;
import com.example.Vehicle_Rental_System.dto.BookingResponseDTO;
import com.example.Vehicle_Rental_System.entity.Booking;
import com.example.Vehicle_Rental_System.entity.Customer;
import com.example.Vehicle_Rental_System.entity.Vehicle;
import com.example.Vehicle_Rental_System.exception.InvalidBookingException;
import com.example.Vehicle_Rental_System.exception.ResourceNotFoundException;
import com.example.Vehicle_Rental_System.exception.VehicleUnavailableException;
import com.example.Vehicle_Rental_System.repository.BookingRepository;
import com.example.Vehicle_Rental_System.repository.CustomerRepository;
import com.example.Vehicle_Rental_System.repository.VehicleRepository;
import com.example.Vehicle_Rental_System.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {

        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw new InvalidBookingException("End date must be after start date");
        }

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer", dto.getCustomerId()));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicle", dto.getVehicleId()));

        if (!vehicle.getAvailabilityStatus()) {
            throw new VehicleUnavailableException(
                    "Vehicle ID " + vehicle.getId() + " is currently unavailable");
        }

        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                dto.getVehicleId(), dto.getStartDate(), dto.getEndDate());

        if (!overlapping.isEmpty()) {
            Booking conflict = overlapping.get(0);
            throw new VehicleUnavailableException(
                    "Vehicle is already booked from "
                            + conflict.getStartDate() + " to " + conflict.getEndDate());
        }

        long totalDays = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        double totalAmount = totalDays * vehicle.getDailyRate();

        Booking booking = Booking.builder()
                .customer(customer)
                .vehicle(vehicle)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .totalDays((int) totalDays)
                .totalAmount(totalAmount)
                .status("ACTIVE")
                .build();

        vehicle.setAvailabilityStatus(false);
        vehicleRepository.save(vehicle);

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {
        return mapToResponse(bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id)));
    }

    @Override
    @Transactional
    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));

        if (!"ACTIVE".equals(booking.getStatus())) {
            throw new InvalidBookingException(
                    "Cannot update a booking with status: " + booking.getStatus());
        }
        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw new InvalidBookingException("End date must be after start date");
        }

        long totalDays = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setTotalDays((int) totalDays);
        booking.setTotalAmount(totalDays * booking.getVehicle().getDailyRate());

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));

        if ("ACTIVE".equals(booking.getStatus())) {
            booking.getVehicle().setAvailabilityStatus(true);
            vehicleRepository.save(booking.getVehicle());
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", customerId);
        }
        return bookingRepository.findByCustomerId(customerId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookingsByVehicle(Long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new ResourceNotFoundException("Vehicle", vehicleId);
        }
        return bookingRepository.findByVehicleId(vehicleId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));

        if (!"ACTIVE".equals(booking.getStatus())) {
            throw new InvalidBookingException(
                    "Only ACTIVE bookings can be cancelled. Status: "
                            + booking.getStatus());
        }

        booking.setStatus("CANCELLED");

        Vehicle vehicle = booking.getVehicle();
        vehicle.setAvailabilityStatus(true);
        vehicleRepository.save(vehicle);

        return mapToResponse(bookingRepository.save(booking));
    }

    private BookingResponseDTO mapToResponse(Booking b) {
        return BookingResponseDTO.builder()
                .id(b.getId())
                .customerId(b.getCustomer().getId())
                .customerName(b.getCustomer().getFullName())
                .vehicleId(b.getVehicle().getId())
                .vehicleBrand(b.getVehicle().getBrand())
                .vehicleModel(b.getVehicle().getModel())
                .startDate(b.getStartDate())
                .endDate(b.getEndDate())
                .totalDays(b.getTotalDays())
                .totalAmount(b.getTotalAmount())
                .status(b.getStatus())
                .build();
    }
}
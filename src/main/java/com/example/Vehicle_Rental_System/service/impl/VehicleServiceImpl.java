package com.example.Vehicle_Rental_System.service.impl;


import com.example.Vehicle_Rental_System.dto.VehicleRequestDTO;
import com.example.Vehicle_Rental_System.dto.VehicleResponseDTO;
import com.example.Vehicle_Rental_System.entity.Vehicle;
import com.example.Vehicle_Rental_System.exception.ResourceNotFoundException;
import com.example.Vehicle_Rental_System.repository.VehicleRepository;
import com.example.Vehicle_Rental_System.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDTO dto) {
        Vehicle v = Vehicle.builder()
                .brand(dto.getBrand())
                .model(dto.getModel())
                .type(dto.getType())
                .registrationNumber(dto.getRegistrationNumber())
                .dailyRate(dto.getDailyRate())
                .availabilityStatus(dto.getAvailabilityStatus() != null
                        ? dto.getAvailabilityStatus() : true)
                .build();
        return mapToResponse(vehicleRepository.save(v));
    }

    @Override
    public List<VehicleResponseDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public VehicleResponseDTO getVehicleById(Long id) {
        return mapToResponse(vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicle",
                        id)));
    }

    @Override
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO dto) {
        Vehicle v = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicle",
                        id));
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setType(dto.getType());
        v.setRegistrationNumber(dto.getRegistrationNumber());
        v.setDailyRate(dto.getDailyRate());
        if (dto.getAvailabilityStatus() != null)
            v.setAvailabilityStatus(dto.getAvailabilityStatus());
        return mapToResponse(vehicleRepository.save(v));
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id))
            throw new ResourceNotFoundException("Vehicle",
                    id);
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<VehicleResponseDTO> getAvailableVehicles() {
        return vehicleRepository.findByAvailabilityStatusTrue()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private VehicleResponseDTO mapToResponse(Vehicle v) {
        return VehicleResponseDTO.builder()
                .id(v.getId())
                .brand(v.getBrand())
                .model(v.getModel())
                .type(v.getType())
                .registrationNumber(v.getRegistrationNumber())
                .dailyRate(v.getDailyRate())
                .availabilityStatus(v.getAvailabilityStatus())
                .build();
    }
}

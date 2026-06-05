package com.example.Vehicle_Rental_System.service;

import com.example.Vehicle_Rental_System.dto.VehicleRequestDTO;
import com.example.Vehicle_Rental_System.dto.VehicleResponseDTO;
import java.util.List;

public interface VehicleService {
    VehicleResponseDTO createVehicle(VehicleRequestDTO dto);
    List<VehicleResponseDTO> getAllVehicles();
    VehicleResponseDTO getVehicleById(Long id);
    VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO dto);
    void deleteVehicle(Long id);
    List<VehicleResponseDTO> getAvailableVehicles();
}
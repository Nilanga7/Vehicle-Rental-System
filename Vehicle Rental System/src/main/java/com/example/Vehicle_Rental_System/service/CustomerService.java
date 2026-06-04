package com.example.Vehicle_Rental_System.service;

import com.example.Vehicle_Rental_System.dto.CustomerRequestDTO;
import com.example.Vehicle_Rental_System.dto.CustomerResponseDTO;
import java.util.List;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO dto);
    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO getCustomerById(Long id);
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto);
    void deleteCustomer(Long id);
}

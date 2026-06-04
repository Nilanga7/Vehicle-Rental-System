package com.example.Vehicle_Rental_System.service.impl;

import com.example.Vehicle_Rental_System.dto.CustomerRequestDTO;
import com.example.Vehicle_Rental_System.dto.CustomerResponseDTO;
import com.example.Vehicle_Rental_System.entity.Customer;
import com.example.Vehicle_Rental_System.exception.ResourceNotFoundException;
import com.example.Vehicle_Rental_System.repository.CustomerRepository;
import com.example.Vehicle_Rental_System.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
        Customer customer = Customer.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .licenseNumber(dto.getLicenseNumber())
                .build();
        return mapToResponse(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        id));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        id));
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setLicenseNumber(dto.getLicenseNumber());
        return mapToResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer",
                    id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO mapToResponse(Customer c) {
        return CustomerResponseDTO.builder()
                .id(c.getId()).fullName(c.getFullName())
                .email(c.getEmail()).phone(c.getPhone())
                .licenseNumber(c.getLicenseNumber()).build();
    }
}
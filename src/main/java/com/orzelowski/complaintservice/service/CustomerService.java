package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.exception.ComplaintDataException;
import com.orzelowski.complaintservice.model.Customer;
import com.orzelowski.complaintservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long customerId) throws ComplaintDataException {
        return customerRepository.findById(customerId).orElseThrow(() -> new ComplaintDataException("Customer with given ID does not exist."));
    }
}

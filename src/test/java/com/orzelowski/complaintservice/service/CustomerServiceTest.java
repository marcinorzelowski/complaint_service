package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.exception.ComplaintDataException;
import com.orzelowski.complaintservice.model.Customer;
import com.orzelowski.complaintservice.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCustomerById_CustomerExists_ReturnsCustomer() {
        //Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        //Act
        Customer foundCustomer = customerService.getCustomerById(1L);

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(customer, foundCustomer);
        });
    }

    @Test
    public void getCustomerById_CustomerNotExists_ThrowsException() {
        //Arrange

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        Exception exception = Assertions.assertThrows(ComplaintDataException.class, () -> customerService.getCustomerById(1L));
        Assertions.assertEquals("Customer with given ID does not exist.", exception.getMessage());
    }
}
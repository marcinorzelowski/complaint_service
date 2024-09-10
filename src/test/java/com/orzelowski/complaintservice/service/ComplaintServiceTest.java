package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.dto.ComplaintRequest;
import com.orzelowski.complaintservice.dto.ComplaintResponse;
import com.orzelowski.complaintservice.dto.ComplaintUpdateRequest;
import com.orzelowski.complaintservice.exception.ComplaintDataException;
import com.orzelowski.complaintservice.mapper.ComplaintMapper;
import com.orzelowski.complaintservice.model.Complaint;
import com.orzelowski.complaintservice.model.Customer;
import com.orzelowski.complaintservice.model.Product;
import com.orzelowski.complaintservice.repository.ComplaintRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ComplaintServiceTest {


    private ComplaintMapper complaintMapper = Mappers.getMapper(ComplaintMapper.class);

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @Mock
    private IPService ipService;

    @InjectMocks
    private ComplaintService complaintService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintService = new ComplaintService(complaintMapper, complaintRepository, customerService, productService, ipService);
    }

    @Test
    public void createComplaint_ComplaintAlreadyExist_IncreaseRequestCounter() {
        //Arrange
        Complaint complaint = new Complaint();
        ComplaintRequest request = new ComplaintRequest("test", 1L, 1L);

        Mockito.when(complaintRepository.findByProductIdAndAuthorId(1L, 1L)).thenReturn(Optional.of(complaint));
        Mockito.when(complaintRepository.save(any(Complaint.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        ComplaintResponse response = complaintService.createComplaint(request, "SOME_IP_ADDRESS");

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(2, response.requestCounter());
        });

    }

    @Test
    public void createComplaint_ComplaintNotExist_CreateNewOne() {
        //Arrange
        Complaint complaint = new Complaint();
        ComplaintRequest request = new ComplaintRequest("test", 1L, 1L);

        Mockito.when(complaintRepository.findByProductIdAndAuthorId(1L, 1L)).thenReturn(Optional.empty());
        Mockito.when(productService.getProductById(1L)).thenReturn(new Product());
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(new Customer());
        Mockito.when(ipService.getCountryBasedOnIP("SOME_IP_ADDRESS")).thenReturn("Poland");
        Mockito.when(complaintRepository.save(any(Complaint.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        ComplaintResponse response = complaintService.createComplaint(request, "SOME_IP_ADDRESS");

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(1, response.requestCounter());
            Assertions.assertEquals("Poland", response.country());
            Assertions.assertEquals("test", response.text());
        });

    }

    @Test
    public void editComplaint_ComplaintNotExist_ThrownException() {
        //Arrange
        Mockito.when(complaintRepository.findById(1L)).thenReturn(Optional.empty());
        ComplaintUpdateRequest request = new ComplaintUpdateRequest(1L, "");

        //Act & Assert
        assertThrows(ComplaintDataException.class, () -> complaintService.editComplaint(request));
    }

    @Test
    public void editComplaint_ComplaintExist_UpdatedComplaintSaved() {
        //Arrange
        ComplaintUpdateRequest updateRequest = new ComplaintUpdateRequest(1L, "UpdatedText");
        Complaint complaint = new Complaint();
        complaint.setText("BeforeUpdate");
        Mockito.when(complaintRepository.findById(1L)).thenReturn(Optional.of(complaint));
        Mockito.when(complaintRepository.save(any(Complaint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //Act
        ComplaintResponse response = complaintService.editComplaint(updateRequest);

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(complaint.getId(), response.id());
            Assertions.assertEquals("UpdatedText", response.text());
        });
    }

    @Test
    public void getComplaint_MultipleComplaintsExists_ReturnList() {
        //Arrange
        Complaint complaint1 = new Complaint();
        Complaint complaint2 = new Complaint();

        Mockito.when(complaintRepository.findAll()).thenReturn(List.of(complaint1, complaint2));
        //Act
        List<ComplaintResponse> response = complaintService.getComplaints();

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(2, response.size());
        });
    }


    @Test
    public void getComplaints_NoComplaintsExists_ReturnEmptyList() {


        Mockito.when(complaintRepository.findAll()).thenReturn(Collections.emptyList());
        //Act
        List<ComplaintResponse> response = complaintService.getComplaints();

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(0, response.size());

        });
    }




}
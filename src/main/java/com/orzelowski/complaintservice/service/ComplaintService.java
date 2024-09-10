package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.dto.ComplaintRequest;
import com.orzelowski.complaintservice.dto.ComplaintResponse;
import com.orzelowski.complaintservice.dto.ComplaintUpdateRequest;
import com.orzelowski.complaintservice.exception.ComplaintDataException;
import com.orzelowski.complaintservice.mapper.ComplaintMapper;
import com.orzelowski.complaintservice.model.Complaint;
import com.orzelowski.complaintservice.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService {
    private final ComplaintMapper complaintMapper;
    private final ComplaintRepository complaintRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final IPService ipService;

    public ComplaintService(ComplaintMapper complaintMapper, ComplaintRepository complaintRepository, CustomerService customerService, ProductService productService, IPService ipService) {
        this.complaintMapper = complaintMapper;
        this.complaintRepository = complaintRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.ipService = ipService;
    }

    public ComplaintResponse createComplaint(ComplaintRequest complaintRequest, String ipAddress) {
        Complaint complaint = complaintRepository.findByProductIdAndAuthorId(complaintRequest.productId(), complaintRequest.authorId())
                .map(this::updateComplaint)
                .orElseGet(() -> createNewComplaint(complaintRequest, ipAddress));

        return complaintMapper.toResponse(complaint);
    }

    private Complaint createNewComplaint(ComplaintRequest complaintRequest, String ipAddress) {
        Complaint complaint = Complaint.builder()
                .product(productService.getProductById(complaintRequest.productId()))
                .author(customerService.getCustomerById(complaintRequest.authorId()))
                .text(complaintRequest.text())
                .country(ipService.getCountryBasedOnIP(ipAddress))
                .build();
        return complaintRepository.save(complaint);
    }

    private Complaint updateComplaint(Complaint complaint) {
        complaint.setRequestCounter(complaint.getRequestCounter() + 1);
        return complaintRepository.save(complaint);
    }

    public ComplaintResponse editComplaint(ComplaintUpdateRequest request) {
        Complaint complaint = complaintRepository.findById(request.id()).orElseThrow(() -> new ComplaintDataException("No complaint with given ID found."));
        complaint.setText(request.text());
        return complaintMapper.toResponse(complaintRepository.save(complaint));
    }

    public List<ComplaintResponse> getComplaints() {
        return complaintRepository.findAll().stream()
                .map(complaintMapper::toResponse)
                .collect(Collectors.toList());
    }

}


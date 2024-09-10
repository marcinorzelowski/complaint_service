package com.orzelowski.complaintservice.controller;

import com.orzelowski.complaintservice.dto.ComplaintRequest;
import com.orzelowski.complaintservice.dto.ComplaintResponse;
import com.orzelowski.complaintservice.dto.ComplaintUpdateRequest;
import com.orzelowski.complaintservice.model.Complaint;
import com.orzelowski.complaintservice.service.ComplaintService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }


    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(@RequestBody ComplaintRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(complaintService.createComplaint(request, httpServletRequest.getRemoteAddr()));
    }

    @PutMapping
    public ResponseEntity<ComplaintResponse> editComplaint(@RequestBody ComplaintUpdateRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintService.editComplaint(request));
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getComplaints() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintService.getComplaints());
    }

}

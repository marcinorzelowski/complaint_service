package com.orzelowski.compliantservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "complaint",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "product_id"})})
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaint_seq_generator")
    @SequenceGenerator(name = "complaint_seq_generator", sequenceName = "complaint_seq", allocationSize = 1)
    private Long id;
    private int requestCounter = 1;
    private String text;
    @ManyToOne
    private Customer author;
    @ManyToOne
    private Product product;
    private LocalDateTime creationDate;

}

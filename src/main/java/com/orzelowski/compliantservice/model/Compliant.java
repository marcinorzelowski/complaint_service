package com.orzelowski.compliantservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "compliant",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "product_id"})})
public class Compliant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compliant_seq_generator")
    @SequenceGenerator(name = "compliant_seq_generator", sequenceName = "compliant_seq", allocationSize = 1)
    private Long id;
    private int requestCounter = 1;
    private String text;
    @ManyToOne
    private Customer author;
    @ManyToOne
    private Product product;
    private LocalDateTime creationDate;
}

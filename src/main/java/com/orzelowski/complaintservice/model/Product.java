package com.orzelowski.complaintservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(name = "product_seq_generator", sequenceName = "product_seq", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Complaint> complaints;
}

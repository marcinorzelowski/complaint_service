package com.orzelowski.compliantservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_generator")
    @SequenceGenerator(name = "customer_seq_generator", sequenceName = "customer_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Complaint> complaints;


}

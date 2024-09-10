package com.orzelowski.complaintservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaint",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"author_id", "product_id"})})
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaint_seq_generator")
    @SequenceGenerator(name = "complaint_seq_generator", sequenceName = "complaint_seq", allocationSize = 1)
    private Long id;
    @Builder.Default
    private int requestCounter = 1;
    private String country;
    private String text;
    @ManyToOne
    private Customer author;
    @ManyToOne
    private Product product;
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();


}

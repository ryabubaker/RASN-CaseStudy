package com.example.rasnassesment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_lines")
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double lineValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


}

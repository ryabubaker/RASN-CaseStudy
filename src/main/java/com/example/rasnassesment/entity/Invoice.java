package com.example.rasnassesment.entity;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private Double paid;

    @Column(nullable = false)
    private Double remaining;

    @Column(nullable = false)
    private String deliveredBy;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceLine> invoiceLines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

}

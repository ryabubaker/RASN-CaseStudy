package com.example.rasnassesment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;


    @Column(nullable = false, length = 12)
    private String phone;

    @Column(nullable = false, length = 256)
    private String service;

    @Column(length = 1000)
    private String note;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();
}

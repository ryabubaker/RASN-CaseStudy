package com.example.rasnassesment.repository;


import com.example.rasnassesment.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.provider.name = :providerName")
    Page<Invoice> findByProviderName(String providerName, Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE DATE(i.dateTime) = DATE(:date)")
    Page<Invoice> findByDate(LocalDateTime date, Pageable pageable);
}

package com.example.rasnassesment.service;

import com.example.rasnassesment.model.request.InvoiceRequest;
import com.example.rasnassesment.model.response.InvoiceResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest invoiceRequest);
    InvoiceResponse getInvoiceById(Long id);
    Page<InvoiceResponse> getInvoicesByProviderName(String providerName, Pageable pageable);
    Page<InvoiceResponse> getInvoicesByDate(LocalDateTime date, Pageable pageable);

    @Transactional
    Page<InvoiceResponse> getAllInvoices(Pageable pageable);

    InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceDTO);
    void deleteInvoice(Long id);
}

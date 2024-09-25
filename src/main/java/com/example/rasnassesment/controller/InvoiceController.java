package com.example.rasnassesment.controller;


import com.example.rasnassesment.model.request.InvoiceRequest;
import com.example.rasnassesment.model.response.InvoiceResponse;
import com.example.rasnassesment.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @PreAuthorize("hasAuthority('INVOICE_ADD')")

    @PostMapping()
    public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse response = invoiceService.createInvoice(invoiceRequest);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('INVOICE_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }
    @PreAuthorize("hasAuthority('INVOICE_VIEW')")
    @GetMapping("/search/provider")
    public ResponseEntity<Page<InvoiceResponse>> getInvoicesByProviderName(
            @RequestParam String providerName,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<InvoiceResponse> response = invoiceService.getInvoicesByProviderName(providerName, pageable);
        return ResponseEntity.ok(response);
    }

    // Search Invoices by Date with Pagination
    @PreAuthorize("hasAuthority('INVOICE_VIEW')")
    @GetMapping("/search/date")
    public ResponseEntity<Page<InvoiceResponse>> getInvoicesByDate(
            @RequestParam String date,  // Expecting date in ISO-8601 format (e.g., "2023-01-01")
            @RequestParam int page,
            @RequestParam int size) {

        LocalDateTime parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE);
        Pageable pageable = PageRequest.of(page, size);
        Page<InvoiceResponse> response = invoiceService.getInvoicesByDate(parsedDate, pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('INVOICE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable Long id,@Valid @RequestBody InvoiceRequest invoiceDTO) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, invoiceDTO));
    }
    @PreAuthorize("hasAuthority('INVOICE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}


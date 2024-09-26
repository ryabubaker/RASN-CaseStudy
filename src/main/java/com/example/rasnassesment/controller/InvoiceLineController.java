package com.example.rasnassesment.controller;

import com.example.rasnassesment.model.request.InvoiceLineRequest;
import com.example.rasnassesment.model.response.InvoiceLineResponse;
import com.example.rasnassesment.model.response.InvoiceResponse;
import com.example.rasnassesment.service.InvoiceLineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invoices/{invoiceId}/lines")
public class InvoiceLineController {


    private final InvoiceLineService invoiceLineService;

    public InvoiceLineController(InvoiceLineService invoiceLineService) {
        this.invoiceLineService = invoiceLineService;
    }

    @PreAuthorize("hasAuthority('INVOICE_ADD')")
    @PostMapping
    public ResponseEntity<InvoiceResponse> addInvoiceLine(@PathVariable Long invoiceId, @RequestBody InvoiceLineRequest dto) {
        // Return the updated InvoiceResponse
        InvoiceResponse responseDTO = invoiceLineService.addInvoiceLine(invoiceId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @PreAuthorize("hasAuthority('INVOICE_UPDATE')")
    @PutMapping("/{lineId}")
    public ResponseEntity<InvoiceResponse> updateInvoiceLine(@PathVariable Long invoiceId, @PathVariable Long lineId, @Valid @RequestBody InvoiceLineRequest dto) {
        InvoiceResponse responseDTO = invoiceLineService.updateInvoiceLine(invoiceId, lineId, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAuthority('INVOICE_DELETE')")
    @DeleteMapping("/{lineId}")
    public ResponseEntity<InvoiceResponse> deleteInvoiceLine(@PathVariable Long invoiceId, @PathVariable Long lineId) {
        InvoiceResponse responseDTO = invoiceLineService.deleteInvoiceLine(invoiceId, lineId);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAuthority('INVOICE_VIEW')")
    @GetMapping
    public ResponseEntity<List<InvoiceLineResponse>> getInvoiceLines(@PathVariable Long invoiceId) {
        List<InvoiceLineResponse> responseDTOs = invoiceLineService.getInvoiceLines(invoiceId);
        return ResponseEntity.ok(responseDTOs);
    }
}

package com.example.rasnassesment.controller;

import com.example.rasnassesment.model.request.InvoiceLineRequestDTO;
import com.example.rasnassesment.model.response.InvoiceLineResponseDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;
import com.example.rasnassesment.service.InvoiceLineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/invoices/{invoiceId}/lines")
public class InvoiceLineController {


    private final InvoiceLineService invoiceLineService;

    public InvoiceLineController(InvoiceLineService invoiceLineService) {
        this.invoiceLineService = invoiceLineService;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> addInvoiceLine(@PathVariable Long invoiceId, @RequestBody InvoiceLineRequestDTO dto) {
        // Return the updated InvoiceResponseDTO
        InvoiceResponseDTO responseDTO = invoiceLineService.addInvoiceLine(invoiceId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{lineId}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoiceLine(@PathVariable Long invoiceId, @PathVariable Long lineId, @RequestBody InvoiceLineRequestDTO dto) {
        InvoiceResponseDTO responseDTO = invoiceLineService.updateInvoiceLine(invoiceId, lineId, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{lineId}")
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Long invoiceId, @PathVariable Long lineId) {
        invoiceLineService.deleteInvoiceLine(invoiceId, lineId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<InvoiceLineResponseDTO>> getInvoiceLines(@PathVariable Long invoiceId) {
        List<InvoiceLineResponseDTO> responseDTOs = invoiceLineService.getInvoiceLines(invoiceId);
        return ResponseEntity.ok(responseDTOs);
    }
}

package com.example.rasnassesment.controller;


import com.example.rasnassesment.model.request.InvoiceRequestDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;
import com.example.rasnassesment.service.InvoiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }



    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO) {
        InvoiceResponseDTO response = invoiceService.createInvoice(invoiceRequestDTO);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequestDTO invoiceDTO) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, invoiceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}


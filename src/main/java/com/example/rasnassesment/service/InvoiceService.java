package com.example.rasnassesment.service;

import com.example.rasnassesment.model.request.InvoiceRequestDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoiceById(Long id);
    InvoiceResponseDTO updateInvoice(Long id, InvoiceRequestDTO invoiceDTO);
    void deleteInvoice(Long id);
}

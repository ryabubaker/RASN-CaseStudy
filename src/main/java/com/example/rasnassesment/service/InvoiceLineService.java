package com.example.rasnassesment.service;

import com.example.rasnassesment.model.request.InvoiceLineRequestDTO;
import com.example.rasnassesment.model.response.InvoiceLineResponseDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceLineService {
    InvoiceResponseDTO addInvoiceLine(Long invoiceId, InvoiceLineRequestDTO dto);
    InvoiceResponseDTO updateInvoiceLine(Long invoiceId, Long lineId, InvoiceLineRequestDTO dto);
    void deleteInvoiceLine(Long invoiceId, Long lineId);
    List<InvoiceLineResponseDTO> getInvoiceLines(Long invoiceId);
}

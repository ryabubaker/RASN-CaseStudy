package com.example.rasnassesment.service;

import com.example.rasnassesment.model.request.InvoiceLineRequest;
import com.example.rasnassesment.model.response.InvoiceLineResponse;
import com.example.rasnassesment.model.response.InvoiceResponse;

import java.util.List;

public interface InvoiceLineService {
    InvoiceResponse addInvoiceLine(Long invoiceId, InvoiceLineRequest dto);
    InvoiceResponse updateInvoiceLine(Long invoiceId, Long lineId, InvoiceLineRequest dto);
    void deleteInvoiceLine(Long invoiceId, Long lineId);
    List<InvoiceLineResponse> getInvoiceLines(Long invoiceId);
}

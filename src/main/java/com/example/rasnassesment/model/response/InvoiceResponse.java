package com.example.rasnassesment.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceResponse {
    private Long id;
    private LocalDateTime dateTime;
    private String providerName;
    private String address;
    private Double total;
    private Double paid;
    private Double remaining;
    private String deliveredBy;
    private List<InvoiceLineResponse> invoiceLines;

}


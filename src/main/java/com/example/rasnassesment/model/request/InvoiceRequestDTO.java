package com.example.rasnassesment.model.request;

import lombok.*;

import java.util.List;


@Data
public class InvoiceRequestDTO {
    private Long providerId;
    private String address;
    private Double paid;
    private String deliveredBy;
    private List<InvoiceLineRequestDTO> invoiceLines;

}
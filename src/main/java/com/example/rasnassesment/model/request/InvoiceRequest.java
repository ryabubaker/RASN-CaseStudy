package com.example.rasnassesment.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequest {

    @NotNull(message = "Provider ID is mandatory")
    private Long providerId;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Paid amount is mandatory")
    private Double paid;

    @NotBlank(message = "Delivered by field is mandatory")
    private String deliveredBy;

    @Valid
    @NotNull(message = "Invoice lines are mandatory")
    private List<InvoiceLineRequest> invoiceLines;
}

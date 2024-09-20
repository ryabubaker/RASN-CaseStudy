package com.example.rasnassesment.model.response;

import lombok.Data;

@Data
public class InvoiceLineResponseDTO {
    private String productName;
    private Integer quantity;
    private Double price;
    private Double lineValue;
}

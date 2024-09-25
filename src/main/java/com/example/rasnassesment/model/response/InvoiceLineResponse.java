package com.example.rasnassesment.model.response;

import lombok.Data;

@Data
public class InvoiceLineResponse {
    private String productName;
    private Integer quantity;
    private Double price;
    private Double lineValue;
}

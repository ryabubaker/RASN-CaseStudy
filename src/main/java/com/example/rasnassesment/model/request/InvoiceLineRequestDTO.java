package com.example.rasnassesment.model.request;

import lombok.*;


@Data
public class InvoiceLineRequestDTO {
    private String productName;
    private Integer quantity;
    private Double price;

}
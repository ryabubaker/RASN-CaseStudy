package com.example.rasnassesment.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class InvoiceLineRequest {

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be at least 0")
    private Double price;
}

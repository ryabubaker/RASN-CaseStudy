package com.example.rasnassesment.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProviderRequest {

    @NotBlank(message = "Provider name is mandatory")
    private String name;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @NotBlank(message = "Service is mandatory")
    private String service;
    private String note;
}

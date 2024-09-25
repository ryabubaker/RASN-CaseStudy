package com.example.rasnassesment.model.response;

import lombok.Data;

@Data
public class ProviderResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String service;
    private String note;
}

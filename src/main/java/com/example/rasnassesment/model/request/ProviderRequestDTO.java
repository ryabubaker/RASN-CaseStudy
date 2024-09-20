package com.example.rasnassesment.model.request;


import lombok.Data;

@Data
public class ProviderRequestDTO {
    private String name;
    private String address;
    private String phone;
    private String service;
    private String note;
}

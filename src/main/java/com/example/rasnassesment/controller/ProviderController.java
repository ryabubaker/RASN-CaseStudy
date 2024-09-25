package com.example.rasnassesment.controller;


import com.example.rasnassesment.model.request.ProviderRequest;

import com.example.rasnassesment.model.response.ProviderResponse;
import com.example.rasnassesment.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("providers")

public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(@Valid @RequestBody ProviderRequest providerRequest) {
        ProviderResponse responseDTO = providerService.createProvider(providerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponse> getProviderById(@PathVariable Long id) {
        ProviderResponse providerResponse = providerService.getProviderById(id);
        return ResponseEntity.ok(providerResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponse>> getAllProviders() {
        List<ProviderResponse> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> updateProvider(@PathVariable Long id,@Valid @RequestBody ProviderRequest providerRequest) {
        ProviderResponse updatedProvider = providerService.updateProvider(id, providerRequest);
        return ResponseEntity.ok(updatedProvider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }


}


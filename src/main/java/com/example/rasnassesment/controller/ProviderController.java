package com.example.rasnassesment.controller;


import com.example.rasnassesment.model.request.ProviderRequestDTO;

import com.example.rasnassesment.model.response.ProviderResponseDTO;
import com.example.rasnassesment.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")

public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderResponseDTO> createProvider(@RequestBody ProviderRequestDTO providerRequestDTO) {
        ProviderResponseDTO responseDTO = providerService.createProvider(providerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> getProviderById(@PathVariable Long id) {
        ProviderResponseDTO providerResponseDTO = providerService.getProviderById(id);
        return ResponseEntity.ok(providerResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponseDTO>> getAllProviders() {
        List<ProviderResponseDTO> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> updateProvider(@PathVariable Long id, @RequestBody ProviderRequestDTO providerRequestDTO) {
        ProviderResponseDTO updatedProvider = providerService.updateProvider(id, providerRequestDTO);
        return ResponseEntity.ok(updatedProvider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }


}


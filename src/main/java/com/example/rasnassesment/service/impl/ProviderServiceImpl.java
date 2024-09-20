package com.example.rasnassesment.service.impl;



import com.example.rasnassesment.entity.Provider;

import com.example.rasnassesment.exceptions.ResourceNotFoundException;
import com.example.rasnassesment.mapper.ProviderMapper;

import com.example.rasnassesment.model.request.ProviderRequestDTO;
import com.example.rasnassesment.model.response.ProviderResponseDTO;
import com.example.rasnassesment.repository.ProviderRepository;
import com.example.rasnassesment.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public ProviderResponseDTO createProvider(ProviderRequestDTO providerRequestDTO) {
        Provider provider = providerMapper.toEntity(providerRequestDTO);
        Provider savedProvider = providerRepository.save(provider);
        return providerMapper.toResponseDTO(savedProvider);
    }
    @Override
    public ProviderResponseDTO getProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        return providerMapper.toResponseDTO(provider);
    }

    @Override
    public List<ProviderResponseDTO> getAllProviders() {
        List<Provider> providers = providerRepository.findAll();
        return providers.stream()
                .map(providerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProviderResponseDTO updateProvider(Long id, ProviderRequestDTO providerRequestDTO) {
        Provider existingProvider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        providerMapper.updateEntity(providerRequestDTO, existingProvider);
        Provider updatedProvider = providerRepository.save(existingProvider);

        return providerMapper.toResponseDTO(updatedProvider);
    }

    @Override
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Provider not found");
        }
        providerRepository.deleteById(id);
    }



}


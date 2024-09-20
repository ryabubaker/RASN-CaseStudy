package com.example.rasnassesment.service;





import com.example.rasnassesment.model.request.ProviderRequestDTO;
import com.example.rasnassesment.model.response.ProviderResponseDTO;

import java.util.List;


public interface ProviderService {

    ProviderResponseDTO createProvider(ProviderRequestDTO providerRequest);
    ProviderResponseDTO getProviderById(Long id);
    List<ProviderResponseDTO> getAllProviders();
    ProviderResponseDTO updateProvider(Long id, ProviderRequestDTO providerRequestDTO);

    void deleteProvider(Long id);

}

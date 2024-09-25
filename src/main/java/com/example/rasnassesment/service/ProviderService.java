package com.example.rasnassesment.service;





import com.example.rasnassesment.model.request.ProviderRequest;
import com.example.rasnassesment.model.response.ProviderResponse;

import java.util.List;


public interface ProviderService {

    ProviderResponse createProvider(ProviderRequest providerRequest);
    ProviderResponse getProviderById(Long id);
    List<ProviderResponse> getAllProviders();
    ProviderResponse updateProvider(Long id, ProviderRequest providerRequest);

    void deleteProvider(Long id);

}

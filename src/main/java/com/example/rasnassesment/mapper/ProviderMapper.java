package com.example.rasnassesment.mapper;


import com.example.rasnassesment.entity.Provider;

import com.example.rasnassesment.model.request.ProviderRequest;
import com.example.rasnassesment.model.response.ProviderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    Provider toEntity(ProviderRequest dto);

    ProviderResponse toResponseDTO(Provider provider);

    void updateEntity(ProviderRequest dto, @MappingTarget Provider provider);
}

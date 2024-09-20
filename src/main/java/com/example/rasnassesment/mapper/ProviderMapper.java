package com.example.rasnassesment.mapper;


import com.example.rasnassesment.entity.Provider;

import com.example.rasnassesment.model.request.ProviderRequestDTO;
import com.example.rasnassesment.model.response.ProviderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    Provider toEntity(ProviderRequestDTO dto);

    ProviderResponseDTO toResponseDTO(Provider provider);

    void updateEntity(ProviderRequestDTO dto, @MappingTarget Provider provider);
}

package com.example.rasnassesment.mapper;


import com.example.rasnassesment.entity.Invoice;
import com.example.rasnassesment.entity.InvoiceLine;
import com.example.rasnassesment.model.request.InvoiceLineRequestDTO;
import com.example.rasnassesment.model.request.InvoiceRequestDTO;
import com.example.rasnassesment.model.response.InvoiceLineResponseDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring", uses = { ProviderMapper.class })
public interface InvoiceMapper {
    // Mapping from InvoiceRequestDTO to Invoice entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "remaining", ignore = true)
    @Mapping(target = "provider", ignore = true)
    Invoice toEntity(InvoiceRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lineValue", ignore = true)
    InvoiceLine toEntity(InvoiceLineRequestDTO dto);

    @Mapping(target = "providerName", source = "provider.name")
    InvoiceResponseDTO toResponseDTO(Invoice invoice);
    @Mapping(target = "lineValue", expression = "java(invoiceLine.getQuantity() * invoiceLine.getPrice())")
    InvoiceLineResponseDTO toResponseDTO(InvoiceLine invoiceLine);
    List<InvoiceLineResponseDTO> toInvoiceLineResponseDTOList(List<InvoiceLine> invoiceLines);

    @Mapping(target = "dateTime", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "remaining", ignore = true)
    void updateEntity(InvoiceRequestDTO invoiceDTO, @MappingTarget Invoice invoice);


}


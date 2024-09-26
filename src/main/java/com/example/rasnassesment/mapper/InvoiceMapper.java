package com.example.rasnassesment.mapper;


import com.example.rasnassesment.entity.Invoice;
import com.example.rasnassesment.entity.InvoiceLine;
import com.example.rasnassesment.model.request.InvoiceLineRequest;
import com.example.rasnassesment.model.request.InvoiceRequest;
import com.example.rasnassesment.model.response.InvoiceLineResponse;
import com.example.rasnassesment.model.response.InvoiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring", uses = { ProviderMapper.class })
public interface InvoiceMapper {

    // Mapping from InvoiceRequest to Invoice entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", expression = "java(java.time.LocalDateTime.now())")  // Setting current date and time
    @Mapping(target = "total", expression = "java(invoiceRequest.getInvoiceLines() != null ? invoiceRequest.getInvoiceLines().stream().mapToDouble(line -> line.getQuantity() * line.getPrice()).sum() : 0.0)")  // Calculate total
    @Mapping(target = "remaining", expression = "java(invoiceRequest.getPaid() - (invoiceRequest.getInvoiceLines() != null ? invoiceRequest.getInvoiceLines().stream().mapToDouble(line -> line.getQuantity() * line.getPrice()).sum() : 0.0))")  // Calculate remaining
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "invoiceLines", source = "invoiceRequest.invoiceLines")
    Invoice toEntity(InvoiceRequest invoiceRequest);

    // Mapping from InvoiceLineRequest to InvoiceLine entity, including lineValue calculation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lineValue", expression = "java(invoiceLineRequest.getQuantity() * invoiceLineRequest.getPrice())")  // Calculate line value
    InvoiceLine toEntity(InvoiceLineRequest invoiceLineRequest);

    // Mapping from Invoice entity to InvoiceResponse DTO
    @Mapping(target = "providerName", source = "provider.name")
    InvoiceResponse toResponseDTO(Invoice invoice);

    // Mapping from InvoiceLine entity to InvoiceLineResponse DTO
    @Mapping(target = "lineValue", expression = "java(invoiceLine.getQuantity() * invoiceLine.getPrice())")  // Calculate line value for response
    InvoiceLineResponse toResponseDTO(InvoiceLine invoiceLine);

    // List mappings for InvoiceLine responses
    List<InvoiceLineResponse> toInvoiceLineResponseDTOList(List<InvoiceLine> invoiceLines);

    // Update entity from InvoiceRequest
    @Mapping(target = "dateTime", ignore = true)
    @Mapping(target = "total", expression = "java(invoice.getInvoiceLines() != null ? invoice.getInvoiceLines().stream().mapToDouble(InvoiceLine::getLineValue).sum() : 0.0)")
    @Mapping(target = "remaining", expression = "java(invoice.getPaid() - (invoice.getInvoiceLines() != null ? invoice.getInvoiceLines().stream().mapToDouble(InvoiceLine::getLineValue).sum() : 0.0))")
    void updateEntity(InvoiceRequest invoiceDTO, @MappingTarget Invoice invoice);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lineValue", expression = "java(invoiceLineRequest.getQuantity() * invoiceLineRequest.getPrice())")
    void updateEntity(InvoiceLineRequest invoiceLineRequest, @MappingTarget InvoiceLine invoiceLine);
}


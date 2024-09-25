package com.example.rasnassesment.service.impl;

import com.example.rasnassesment.entity.Invoice;
import com.example.rasnassesment.entity.InvoiceLine;
import com.example.rasnassesment.entity.Provider;
import com.example.rasnassesment.exceptions.ResourceNotFoundException;
import com.example.rasnassesment.mapper.InvoiceMapper;
import com.example.rasnassesment.model.request.InvoiceRequest;
import com.example.rasnassesment.model.response.InvoiceResponse;
import com.example.rasnassesment.repository.InvoiceRepository;
import com.example.rasnassesment.repository.ProviderRepository;
import com.example.rasnassesment.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final ProviderRepository providerRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, ProviderRepository providerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.providerRepository = providerRepository;
    }

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {
        Provider provider = providerRepository.findById(invoiceRequest.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        Invoice invoice = invoiceMapper.toEntity(invoiceRequest);
        invoice.setProvider(provider);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(savedInvoice);
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceDTO) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        if (invoiceDTO.getProviderId() != null) {
            Provider provider = providerRepository.findById(invoiceDTO.getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
            invoice.setProvider(provider);
        }

        invoiceMapper.updateEntity(invoiceDTO, invoice);
        return invoiceMapper.toResponseDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

    @Override
    public Page<InvoiceResponse> getInvoicesByProviderName(String providerName, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByProviderName(providerName, pageable);
        return invoices.map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponse> getInvoicesByDate(LocalDateTime date, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByDate(date, pageable);
        return invoices.map(invoiceMapper::toResponseDTO);
    }
}


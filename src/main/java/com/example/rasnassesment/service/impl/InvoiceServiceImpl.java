package com.example.rasnassesment.service.impl;


import com.example.rasnassesment.entity.Invoice;
import com.example.rasnassesment.entity.InvoiceLine;
import com.example.rasnassesment.entity.Provider;
import com.example.rasnassesment.exceptions.ResourceNotFoundException;
import com.example.rasnassesment.mapper.InvoiceMapper;
import com.example.rasnassesment.model.request.InvoiceRequestDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;
import com.example.rasnassesment.repository.InvoiceRepository;
import com.example.rasnassesment.repository.ProviderRepository;
import com.example.rasnassesment.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final  ProviderRepository providerRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, ProviderRepository providerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.providerRepository = providerRepository;
    }

    @Override
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO invoiceRequestDTO) {

        Provider provider = providerRepository.findById(invoiceRequestDTO.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));        Invoice invoice = invoiceMapper.toEntity(invoiceRequestDTO);

        if (invoice.getInvoiceLines() != null && !invoice.getInvoiceLines().isEmpty()) {
            for (InvoiceLine line : invoice.getInvoiceLines()) {
                line.setInvoice(invoice);

                double lineValue = line.getQuantity() * line.getPrice();
                line.setLineValue(lineValue);
            }
        }

        double total = calculateTotal(invoice.getInvoiceLines());
        invoice.setTotal(total);

        double remaining = invoice.getPaid() - total;
        invoice.setRemaining(remaining);

        invoice.setDateTime(LocalDateTime.now());
        invoice.setProvider(provider);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(savedInvoice);
    }

    private double calculateTotal(List<InvoiceLine> invoiceLines) {
        if (invoiceLines == null || invoiceLines.isEmpty()) {
            return 0.0;
        }

        return invoiceLines.stream()
                .mapToDouble(InvoiceLine::getLineValue)
                .sum();
    }



    @Override
    public InvoiceResponseDTO getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }

    @Override
    public InvoiceResponseDTO updateInvoice(Long id, InvoiceRequestDTO invoiceDTO) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        if (invoiceDTO.getProviderId() != null) {
            Provider provider = providerRepository.findById(invoiceDTO.getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
            invoice.setProvider(provider);
        }

        invoiceMapper.updateEntity(invoiceDTO, invoice);

        invoice.setTotal(calculateTotal(invoice.getInvoiceLines()));
        invoice.setRemaining(invoice.getTotal() - invoice.getPaid());

        return invoiceMapper.toResponseDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);

    }



}



package com.example.rasnassesment.service.impl;

import com.example.rasnassesment.entity.Invoice;
import com.example.rasnassesment.entity.InvoiceLine;
import com.example.rasnassesment.exceptions.ResourceNotFoundException;
import com.example.rasnassesment.mapper.InvoiceMapper;
import com.example.rasnassesment.model.request.InvoiceLineRequestDTO;
import com.example.rasnassesment.model.response.InvoiceLineResponseDTO;
import com.example.rasnassesment.model.response.InvoiceResponseDTO;
import com.example.rasnassesment.repository.InvoiceLineRepository;
import com.example.rasnassesment.repository.InvoiceRepository;
import com.example.rasnassesment.service.InvoiceLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceLineServiceImpl implements InvoiceLineService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public InvoiceResponseDTO addInvoiceLine(Long invoiceId, InvoiceLineRequestDTO dto) {
        // Fetch the invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        // Map the DTO to an entity
        InvoiceLine invoiceLine = invoiceMapper.toEntity(dto);
        invoiceLine.setInvoice(invoice);
        invoiceLine.setLineValue(invoiceLine.getQuantity() * invoiceLine.getPrice());

        // Save the new invoice line
        invoiceLineRepository.save(invoiceLine);

        // Update the invoice's total and remaining
        updateInvoiceTotals(invoice);

        // Return the response DTO
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public InvoiceResponseDTO updateInvoiceLine(Long invoiceId, Long lineId, InvoiceLineRequestDTO dto) {
        // Fetch the invoice line
        InvoiceLine invoiceLine = invoiceLineRepository.findById(lineId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice line not found"));

        // Update the line details
        invoiceLine.setProductName(dto.getProductName());
        invoiceLine.setQuantity(dto.getQuantity());
        invoiceLine.setPrice(dto.getPrice());
        invoiceLine.setLineValue(invoiceLine.getQuantity() * invoiceLine.getPrice());

        // Save the updated line
        invoiceLineRepository.save(invoiceLine);

        // Fetch the parent invoice and update its totals
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        updateInvoiceTotals(invoice);

        // Return the updated response DTO
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public void deleteInvoiceLine(Long invoiceId, Long lineId) {
        // Fetch the invoice line
        InvoiceLine invoiceLine = invoiceLineRepository.findById(lineId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice line not found"));

        // Delete the line
        invoiceLineRepository.delete(invoiceLine);

        // Fetch the parent invoice and update its totals
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        updateInvoiceTotals(invoice);
    }

    @Override
    public List<InvoiceLineResponseDTO> getInvoiceLines(Long invoiceId) {
        // Fetch all invoice lines associated with an invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        return invoice.getInvoiceLines().stream()
                .map(invoiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    private void updateInvoiceTotals(Invoice invoice) {
        double total = invoice.getInvoiceLines().stream()
                .mapToDouble(InvoiceLine::getLineValue)
                .sum();
        invoice.setTotal(total);
        invoice.setRemaining(invoice.getPaid() - total);
        invoiceRepository.save(invoice);
    }
}

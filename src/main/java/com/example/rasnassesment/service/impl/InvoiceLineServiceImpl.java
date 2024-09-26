package com.example.rasnassesment.service.impl;

import com.example.rasnassesment.entity.Invoice;
import com.example.rasnassesment.entity.InvoiceLine;
import com.example.rasnassesment.exceptions.ResourceNotFoundException;
import com.example.rasnassesment.mapper.InvoiceMapper;
import com.example.rasnassesment.model.request.InvoiceLineRequest;
import com.example.rasnassesment.model.response.InvoiceLineResponse;
import com.example.rasnassesment.model.response.InvoiceResponse;
import com.example.rasnassesment.repository.InvoiceLineRepository;
import com.example.rasnassesment.repository.InvoiceRepository;
import com.example.rasnassesment.service.InvoiceLineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceLineServiceImpl implements InvoiceLineService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceLineRepository invoiceLineRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceLineServiceImpl(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository, InvoiceLineRepository invoiceLineRepository) {
        this.invoiceMapper = invoiceMapper;
        this.invoiceRepository = invoiceRepository;
        this.invoiceLineRepository = invoiceLineRepository;
    }

    @Override
    public InvoiceResponse addInvoiceLine(Long invoiceId, InvoiceLineRequest dto) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        InvoiceLine invoiceLine = invoiceMapper.toEntity(dto);
        invoiceLine.setInvoice(invoice);

        invoiceLineRepository.save(invoiceLine);

        updateInvoiceTotals(invoice);
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public InvoiceResponse updateInvoiceLine(Long invoiceId, Long lineId, InvoiceLineRequest dto) {
        InvoiceLine invoiceLine = invoiceLineRepository.findById(lineId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice line not found"));

        invoiceMapper.updateEntity(dto, invoiceLine);
        invoiceLineRepository.save(invoiceLine);

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public InvoiceResponse deleteInvoiceLine(Long invoiceId, Long lineId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        InvoiceLine invoiceLine = invoiceLineRepository.findById(lineId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice line not found"));

        invoiceLineRepository.delete(invoiceLine);

        // Recalculate the totals
        updateInvoiceTotals(invoice);

        return invoiceMapper.toResponseDTO(invoice);
    }



    @Override
    public List<InvoiceLineResponse> getInvoiceLines(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        return invoiceMapper.toInvoiceLineResponseDTOList(invoice.getInvoiceLines());
    }

    @Override
    public InvoiceLineResponse getInvoiceLineById(Long invoiceId, Long lineId) {
        invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        InvoiceLine invoiceLine = invoiceLineRepository.findById(lineId)
                .orElseThrow(() -> new ResourceNotFoundException("InvoiceLine not found with id: " + lineId));
        return invoiceMapper.toResponseDTO(invoiceLine);
    }

    private void updateInvoiceTotals(Invoice invoice) {
        double total = invoice.getInvoiceLines().stream()
                .mapToDouble(InvoiceLine::getLineValue)
                .sum();
        invoice.setTotal(total);

        double remaining = invoice.getPaid() - total;
        invoice.setRemaining(remaining);

        invoiceRepository.save(invoice);
    }


}


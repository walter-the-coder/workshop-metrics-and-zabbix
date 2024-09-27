package com.example.service;

import com.example.controller.dto.ReceptionDto;
import com.example.controller.dto.ReceptionResponse;
import com.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceptionService {

    private final InputValidationService inputValidationService;
    private final TransactionRepository transactionRepository;

    public ReceptionService(
            InputValidationService inputValidationService,
            TransactionRepository transactionRepository
    ) {
        this.inputValidationService = inputValidationService;
        this.transactionRepository = transactionRepository;
    }

    public ReceptionResponse handleReceivedData(ReceptionDto data) {
        inputValidationService.validateOrThrowError(data);

        transactionRepository.storeReceivedData(data);

        return new ReceptionResponse(data.getOrganisationNumber());
    }
}

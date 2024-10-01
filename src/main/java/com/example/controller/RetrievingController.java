package com.example.controller;

import com.example.controller.dto.ReceptionDto;
import com.example.controller.dto.RetrievingDto;
import com.example.repository.TransactionRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retrieving")
public class RetrievingController {
    private final TransactionRepository transactionRepository;

    public RetrievingController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping(value = "/unprocessed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RetrievingDto> handleData() {
        List<ReceptionDto> unprocessedData = transactionRepository.getUnprocessedData();
        return ResponseEntity.ok(new RetrievingDto(unprocessedData));
    }
}

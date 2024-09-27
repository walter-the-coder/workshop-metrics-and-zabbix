package com.example.controller;

import com.example.controller.dto.ReceptionDto;
import com.example.controller.dto.ReceptionResponse;
import com.example.controller.dto.ValidationResponse;
import com.example.service.InputValidationService;
import com.example.service.ReceptionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reception")
public class ReceptionController {

    private final ReceptionService receptionService;
    private final InputValidationService inputValidationService;

    public ReceptionController(
            ReceptionService receptionService,
            InputValidationService inputValidationService
    ) {
        this.receptionService = receptionService;
        this.inputValidationService = inputValidationService;
    }

    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationResponse> validate(
            @RequestBody ReceptionDto data
    ) {
        ValidationResponse response = inputValidationService.validate(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReceptionResponse> handleData(
            @RequestBody ReceptionDto data
    ) {
        ReceptionResponse response = receptionService.handleReceivedData(data);
        return ResponseEntity.ok(response);
    }
}

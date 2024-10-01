package com.example.service;

import com.example.controller.dto.*;
import com.example.exceptionHandling.CustomRuntimeException;
import com.example.type.TaxationPeriodType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class InputValidationService {

    public ValidationResponse validate(ReceptionDto data) {
        Map<String, String> validationErrors = new HashMap<>();

        validateYear(data.getYear(), validationErrors);

        validateTaxationPeriodType(data.getTaxationPeriodType(), validationErrors);

        validateVATLines(data.getVatLines(), validationErrors);

        return new ValidationResponse(validationErrors);
    }

    public void validateOrThrowError(ReceptionDto data) {
        ValidationResponse validationResponse = validate(data);

        if (!validationResponse.getValidationErrors().isEmpty()) {
            String errorMessage = StringUtils.joinWith(":", validationResponse.getValidationErrors().values())
                    .replace("[", "")
                    .replace("]", "");

            throw new CustomRuntimeException(
                    "BAD_REQUEST",
                    errorMessage,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public void validateYear(Integer year, Map<String, String> validationErrors) {
        if (year == null || year != 2024) {
            validationErrors.put("year", "Invalid year: " + year + ". "
                    + "We only allow submissions for year 2024 at the moment");
        }
    }

    public void validateTaxationPeriodType(
        TaxationPeriodType taxationPeriodType,
        Map<String, String> validationErrors
    ) {
        if (taxationPeriodType != TaxationPeriodType.JAN_FEB) {
            validationErrors.put("taxationPeriodType", "Invalid taxation period type: " + taxationPeriodType
                + ". We only allow submissions of the taxation period january-february at the moment.");
        }
    }

    public void validateVATLines(VATLines vatLines, Map<String, String> validationErrors) {
        for (VATLine vatLine : vatLines.getVatLines()) {
            validateVATLine(vatLine, validationErrors);
        }
    }

    public void validateVATLine(VATLine vatLine, Map<String, String> validationErrors) {
        Set<VATCode> allowedPositiveCodes = new HashSet<>();
        allowedPositiveCodes.add(VATCode.VAT_CODE_1);
        allowedPositiveCodes.add(VATCode.VAT_CODE_2);
        allowedPositiveCodes.add(VATCode.VAT_CODE_8);
        allowedPositiveCodes.add(VATCode.VAT_CODE_9);
        allowedPositiveCodes.add(VATCode.VAT_CODE_10);

        Set<VATCode> allowedNegativeCodes = new HashSet<>();
        allowedNegativeCodes.add(VATCode.VAT_CODE_3);
        allowedNegativeCodes.add(VATCode.VAT_CODE_4);
        allowedNegativeCodes.add(VATCode.VAT_CODE_5);
        allowedNegativeCodes.add(VATCode.VAT_CODE_6);
        allowedNegativeCodes.add(VATCode.VAT_CODE_7);

        if (allowedPositiveCodes.contains(vatLine.getVatCode())) {
            if (vatLine.getAmount() > 0) {
                validationErrors.put("vatLine",
                    "Invalid VAT amount for VAT code " + vatLine.getVatCode()
                        + ". The amount should be zero or negative!");
            }
        } else if (allowedNegativeCodes.contains(vatLine.getVatCode())) {
            if (vatLine.getAmount() < 0) {
                validationErrors.put("vatLine",
                    "Invalid VAT amount for VAT code " + vatLine.getVatCode()
                        + ". The amount should be zero or positive!");
            }
        } else {
            throw new CustomRuntimeException(
                "INVALID_VAT_VODE",
                "Invalid VAT code " + vatLine.getVatCode(),
                HttpStatus.BAD_REQUEST
            );
        }
    }
}

package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.client.AuthorizationClient;
import com.example.exceptionHandling.CustomRuntimeException;
import com.example.type.OrganisationNumber;
import com.example.type.TaxpayerIdentificationNumber;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;
    private final Boolean authorizationEnabled;

    public AuthorizationService(
        AuthorizationClient authorizationClient,
        @Value("${security.authorization.enabled:true}") Boolean authorizationEnabled
    ) {
        this.authorizationClient = authorizationClient;
        this.authorizationEnabled = authorizationEnabled;
    }

    public void controlUserAccessToOrganisation(
        TaxpayerIdentificationNumber TaxpayerIdentificationNumber,
        OrganisationNumber organisationNumber
    ) {
        if (!authorizationEnabled) {
            return;
        }

        List<OrganisationNumber> usersOrganisations = authorizationClient.hasAccessToOrganisations(
            TaxpayerIdentificationNumber);
        if (!usersOrganisations.contains(organisationNumber)) {
            throw new CustomRuntimeException(
                "NOT_AUTHORIZED",
                "User with id " + TaxpayerIdentificationNumber + " is not authorized to access organisation "
                    + organisationNumber,
                HttpStatus.UNAUTHORIZED
            );
        }
    }
}

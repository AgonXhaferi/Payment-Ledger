package com.agon.ledger.paymentledgerservice._interface.http.mapper;

import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class HttpExceptionMapper {
    public RuntimeException map(DomainError error) {
        return switch (error) {
            case DomainError.NotFound e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.message());

            case DomainError.Validation e -> new ResponseStatusException(HttpStatus.BAD_REQUEST, e.message());

            case DomainError.Conflict e -> new ResponseStatusException(HttpStatus.CONFLICT, e.message());
        };
    }
}
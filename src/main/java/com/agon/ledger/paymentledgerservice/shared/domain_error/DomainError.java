package com.agon.ledger.paymentledgerservice.shared.domain_error;


public sealed interface DomainError permits
        DomainError.NotFound,
        DomainError.Validation,
        DomainError.Conflict {

    String message();

    String code();

    record NotFound(String message, String resourceId) implements DomainError {
        @Override
        public String code() {
            return "RESOURCE_NOT_FOUND";
        }
    }

    record Validation(String message) implements DomainError {
        @Override
        public String code() {
            return "VALIDATION_ERROR";
        }
    }

    record Conflict(String message) implements DomainError {
        @Override
        public String code() {
            return "CONFLICT";
        }
    }
}
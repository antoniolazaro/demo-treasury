package com.purshase.transaction.test.domain.exceptions.badrequest;

public class IntegrationBadRequestException extends AbstractBadRequestException {
        public IntegrationBadRequestException(String message) {
            super(message);
        }
        public IntegrationBadRequestException(String message, Throwable cause) {
            super(message, cause);
        }
}

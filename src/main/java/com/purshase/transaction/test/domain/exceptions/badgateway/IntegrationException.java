package com.purshase.transaction.test.domain.exceptions.badgateway;

public class IntegrationException extends AbstractBadGatewayException {
        public IntegrationException(String message) {
            super(message);
        }
        public IntegrationException(String message, Throwable cause) {
            super(message, cause);
        }
}

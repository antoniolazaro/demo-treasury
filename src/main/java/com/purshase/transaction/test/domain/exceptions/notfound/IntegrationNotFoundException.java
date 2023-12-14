package com.purshase.transaction.test.domain.exceptions.notfound;

public class IntegrationNotFoundException extends AbstractNotFoundException {
        public IntegrationNotFoundException(String message) {
            super(message);
        }
        public IntegrationNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
}

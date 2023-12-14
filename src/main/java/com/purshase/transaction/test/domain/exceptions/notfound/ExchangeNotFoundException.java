package com.purshase.transaction.test.domain.exceptions.notfound;

public class ExchangeNotFoundException extends AbstractNotFoundException {
        public ExchangeNotFoundException(String message) {
            super(message);
        }
        public ExchangeNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
}

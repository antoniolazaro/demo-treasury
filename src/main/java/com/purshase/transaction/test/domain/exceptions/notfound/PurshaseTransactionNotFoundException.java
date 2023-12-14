package com.purshase.transaction.test.domain.exceptions.notfound;

public class PurshaseTransactionNotFoundException extends AbstractNotFoundException {
        public PurshaseTransactionNotFoundException(String message) {
            super(message);
        }
        public PurshaseTransactionNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
}

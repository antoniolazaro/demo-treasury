package com.purshase.transaction.test.domain.exceptions;

public abstract class AbstractPurshaseException extends RuntimeException{
        public AbstractPurshaseException(String message) {
            super(message);
        }
        public AbstractPurshaseException(String message, Throwable cause) {
            super(message, cause);
        }
}

package com.purshase.transaction.test.domain.exceptions.internalservererror;

import com.purshase.transaction.test.domain.exceptions.AbstractPurshaseException;

public abstract class AbstractInternalServerErrorException extends AbstractPurshaseException {
        public AbstractInternalServerErrorException(String message) {
            super(message);
        }
        public AbstractInternalServerErrorException(String message, Throwable cause) {
            super(message, cause);
        }
}

package com.purshase.transaction.test.domain.exceptions.badrequest;

import com.purshase.transaction.test.domain.exceptions.AbstractPurshaseException;

public abstract class AbstractBadRequestException extends AbstractPurshaseException {
        public AbstractBadRequestException(String message) {
            super(message);
        }
        public AbstractBadRequestException(String message, Throwable cause) {
            super(message, cause);
        }
}

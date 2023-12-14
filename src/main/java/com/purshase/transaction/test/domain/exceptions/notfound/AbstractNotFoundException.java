package com.purshase.transaction.test.domain.exceptions.notfound;

import com.purshase.transaction.test.domain.exceptions.AbstractPurshaseException;

public abstract class AbstractNotFoundException extends AbstractPurshaseException {
        public AbstractNotFoundException(String message) {
            super(message);
        }
        public AbstractNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
}

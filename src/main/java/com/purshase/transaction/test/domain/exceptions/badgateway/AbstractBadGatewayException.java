package com.purshase.transaction.test.domain.exceptions.badgateway;

import com.purshase.transaction.test.domain.exceptions.AbstractPurshaseException;

public abstract class AbstractBadGatewayException extends AbstractPurshaseException {
        public AbstractBadGatewayException(String message) {
            super(message);
        }
        public AbstractBadGatewayException(String message, Throwable cause) {
            super(message, cause);
        }
}

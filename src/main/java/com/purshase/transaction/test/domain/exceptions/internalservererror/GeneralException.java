package com.purshase.transaction.test.domain.exceptions.internalservererror;

public class GeneralException extends AbstractInternalServerErrorException {
        public GeneralException(String message) {
            super(message);
        }
        public GeneralException(String message, Throwable cause) {
            super(message, cause);
        }
}

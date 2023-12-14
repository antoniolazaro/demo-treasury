package com.purshase.transaction.test.application.component.integration.feign.config;

import com.purshase.transaction.test.domain.exceptions.badgateway.IntegrationException;
import com.purshase.transaction.test.domain.exceptions.badrequest.IntegrationBadRequestException;
import com.purshase.transaction.test.domain.exceptions.notfound.IntegrationNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ErrorDecoderFeign implements ErrorDecoder {

    private final Map<HttpStatus, Class<? extends RuntimeException>> INTEGRATION_EXCEPTIONS =  Map.of(
            HttpStatus.BAD_REQUEST, IntegrationBadRequestException.class,
            HttpStatus.NOT_FOUND, IntegrationNotFoundException.class,
            HttpStatus.INTERNAL_SERVER_ERROR, IntegrationException.class
    );
    @Override
    public Exception decode(String s, Response response) {

        try {
            var statusCode = HttpStatus.resolve(response.status());
            log.error("statusCode {}", statusCode);
            var exception = INTEGRATION_EXCEPTIONS.getOrDefault(statusCode, IntegrationException.class);
            log.error("exception {}", exception);
            var errorMessage = getErrorMessage(response);
            return exception.getConstructor(String.class).newInstance(errorMessage);
        }catch (Exception e) {
            log.error("Error decoding feign", e);
            return new IntegrationException("Error decoding feign", e);
        }
    }
    private String getErrorMessage(Response response) throws IOException {
        if (Objects.nonNull(response.body()) && Objects.nonNull(response.body().asInputStream())) {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }
        return null;
    }
}

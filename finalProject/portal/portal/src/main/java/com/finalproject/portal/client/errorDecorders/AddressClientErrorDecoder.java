package com.finalproject.portal.client.errorDecorders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.AddressNotFoundException;
import com.finalproject.portal.exception.CollaboratorNotFoundException;
import com.finalproject.portal.exception.ErrorResponse;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AddressClientErrorDecoder implements ErrorDecoder {

    ObjectMapper objectMapper;

    public AddressClientErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        if (response.body() != null) {

            String responseBody = getErrorResponseToString(response);

            ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

            switch (response.status()) {
                case 404:
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.COLLABORATOR_NOT_FOUND) {
                        throw new CollaboratorNotFoundException(errorResponse.getErrorResponseCode(),
                                errorResponse.getStatus(), errorResponse.getMessage(),
                                errorResponse.getParams());
                    } else if (errorResponse.getErrorResponseCode() == ErrorResponseCode.ADDRESS_NOT_FOUND) {
                        throw new AddressNotFoundException(errorResponse.getErrorResponseCode(),
                                errorResponse.getStatus(), errorResponse.getMessage(),
                                errorResponse.getParams());
                    }

                case 409:
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.OPTIMISTIC_LOCKING_FAILURE) {
                        throw new ObjectOptimisticLockingFailureException(errorResponse.getClass(), errorResponse.getParams());
                    }

            }
        }

        return null;
    }

    private String getErrorResponseToString(Response response) {
        try {
            return Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

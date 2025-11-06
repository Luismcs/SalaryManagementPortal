package com.finalproject.portal.client.errorDecorders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.CollaboratorNotFoundException;
import com.finalproject.portal.exception.ErrorResponse;
import com.finalproject.portal.exception.RoleNotFoundException;
import com.finalproject.portal.exception.UserCredentialsNotFound;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserCredentialsClientErrorDecoder implements ErrorDecoder {

    ObjectMapper objectMapper;

    public UserCredentialsClientErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        if (response.body() != null) {

            String responseBody = getErrorResponseToString(response);
            System.out.println(responseBody);

            ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

            switch (response.status()) {
                case 404:
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND) {
                        throw new UserCredentialsNotFound(errorResponse.getErrorResponseCode(),
                                errorResponse.getStatus(), errorResponse.getMessage(),
                                Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.ROLE_NOT_FOUND) {
                        throw new RoleNotFoundException(errorResponse.getErrorResponseCode(),
                                errorResponse.getStatus(), errorResponse.getMessage(),
                                Long.parseLong(errorResponse.getParams()));
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

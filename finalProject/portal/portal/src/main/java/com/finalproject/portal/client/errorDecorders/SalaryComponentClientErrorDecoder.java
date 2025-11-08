package com.finalproject.portal.client.errorDecorders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.ComponentTypeNotFoundException;
import com.finalproject.portal.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.portal.exception.ErrorResponse;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SalaryComponentClientErrorDecoder implements ErrorDecoder {

    ObjectMapper objectMapper;

    public SalaryComponentClientErrorDecoder(ObjectMapper objectMapper) {
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
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND) {
                        throw new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND, 404,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND) {
                        throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, 404,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.SALARY_COMPONENT_NOT_FOUND) {
                        throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.SALARY_COMPONENT_NOT_FOUND, 404,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
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

package com.finalproject.portal.client.errorDecorders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.*;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class SalaryClientErrorDecoder implements ErrorDecoder {

    ObjectMapper objectMapper;

    public SalaryClientErrorDecoder(ObjectMapper objectMapper) {
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
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.SALARY_NOT_FOUND) {
                        throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND, 404,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                case 409:
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.SALARY_IS_ALREADY_ACTIVE) {
                        throw new SalaryAlreadyActiveException(ErrorResponseCode.SALARY_IS_ALREADY_ACTIVE, 409,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.SALARY_IS_ALREADY_APPROVED) {
                        throw new SalaryAlreadyApprovedException(ErrorResponseCode.SALARY_IS_ALREADY_APPROVED, 409,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.SALARY_CANNOT_BE_APPROVED_ANYMORE) {
                        throw new SalaryCannotBeApprovedException(ErrorResponseCode.SALARY_CANNOT_BE_APPROVED_ANYMORE, 409,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.SALARY_CANNOT_BE_UPDATED_ANYMORE) {
                        throw new SalaryCannotBeUpdatedException(ErrorResponseCode.SALARY_CANNOT_BE_UPDATED_ANYMORE, 409,
                                errorResponse.getMessage(), Long.parseLong(errorResponse.getParams()));
                    }
                    if (errorResponse.getErrorResponseCode() == ErrorResponseCode.DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE) {
                        throw new DuplicateSalaryForImplementationDateException(ErrorResponseCode.DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE, 409,
                                errorResponse.getMessage(), LocalDate.parse(errorResponse.getParams()));
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

package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ExportRequestDTO {

    @Schema(description = "The Export Request's emails", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The export request's emails cannot be null")
    List<String> emails;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}

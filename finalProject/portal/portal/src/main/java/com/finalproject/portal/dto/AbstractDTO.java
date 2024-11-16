package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class AbstractDTO {

    @Schema(description = "Object's unique Id", example = "1")
    private Long id;

    @Schema(description = "User that created this Object", example = "admin")
    private String createdBy;

    @Schema(description = "Object's creation date", example = "2023-01-15T14:00:00Z")
    private Date createdDate;

    @Schema(description = "User that modified this object last time", example = "admin")
    private String lastModifiedBy;

    @Schema(description = "Object's last modified date", example = "2023-01-20T18:30:00Z")
    private Date lastModifiedDate;

    @Schema(description = "Object's current version", example = "1")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

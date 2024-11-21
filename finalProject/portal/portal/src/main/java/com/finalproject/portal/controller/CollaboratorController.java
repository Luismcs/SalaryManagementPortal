package com.finalproject.portal.controller;

import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.UserGeneralInfoDTO;
import com.finalproject.portal.service.CollaboratorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portal Collaborators", description = "Collaborator Management Endpoints")
@RestController
@RequestMapping("/collaborators")
@SecurityRequirement(name = "bearerAuth")
public class CollaboratorController {

    private final CollaboratorServiceImpl collaboratorServiceImpl;

    public CollaboratorController(CollaboratorServiceImpl collaboratorServiceImpl) {
        this.collaboratorServiceImpl = collaboratorServiceImpl;
    }

    @Operation(
            summary = "Gets all the collaborator",
            description = "Returns a paginated collaborator list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Collaborator list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<CollaboratorDTO>> getAll(Pageable pageable) {
        return collaboratorServiceImpl.getAll(pageable);
    }

    @Operation(
            summary = "Gets a collaborator by id",
            description = "Returns a collaborator ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Collaborator list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CollaboratorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Collaborator not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> getById(@PathVariable long id) {
        return collaboratorServiceImpl.getById(id);
    }

    @Operation(
            summary = "Creates a collaborator",
            description = "Returns the created collaborator",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Collaborator created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<CollaboratorDTO> create(@Valid @RequestBody UserGeneralInfoDTO userGeneralInfoDTO) {
        return collaboratorServiceImpl.addCollaborator(userGeneralInfoDTO);
    }

    @Operation(
            summary = "Updates a collaborator",
            description = "Returns the updated collaborator ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Collaborator list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CollaboratorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Collaborator not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<CollaboratorDTO> update(@Valid @RequestBody CollaboratorDTO collaboratorDTO) {
        return collaboratorServiceImpl.update(collaboratorDTO);
    }

    @Operation(
            summary = "Deletes a collaborator",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Collaborator deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CollaboratorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Collaborator not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return collaboratorServiceImpl.delete(id);
    }

}

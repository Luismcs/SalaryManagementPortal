package com.finalproject.collaborator.controller;

import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.service.impl.CollaboratorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Collaborators", description = "Collaborator Management Endpoints")
@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    private final CollaboratorServiceImpl collaboratorService;

    public CollaboratorController(CollaboratorServiceImpl collaboratorService) {
        this.collaboratorService = collaboratorService;
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
    public ResponseEntity<Page<CollaboratorDTO>> getAll(@ParameterObject
                                                        @PageableDefault(size = 20) Pageable pageable) {
        Page<CollaboratorDTO> page = collaboratorService.getAll(pageable);
        return ResponseEntity.ok(page);
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
    public ResponseEntity<CollaboratorDTO> getById(@PathVariable long id) throws CollaboratorNotFoundException {
        CollaboratorDTO foundCollaboratorDTO = collaboratorService.getById(id);
        return ResponseEntity.ok(foundCollaboratorDTO);
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
    public ResponseEntity<CollaboratorDTO> create(@Valid @RequestBody CollaboratorDTO collaboratorDTO) {
        CollaboratorDTO savedCollaboratorDTO = collaboratorService.add(collaboratorDTO);
        return ResponseEntity.ok(savedCollaboratorDTO);
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
    public ResponseEntity<CollaboratorDTO> update(@Valid @RequestBody CollaboratorDTO collaboratorDTO) throws CollaboratorNotFoundException {
        CollaboratorDTO savedCollaboratorDTO = collaboratorService.update(collaboratorDTO);
        return ResponseEntity.ok(savedCollaboratorDTO);
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
    public ResponseEntity<CollaboratorDTO> delete(@PathVariable long id) throws CollaboratorNotFoundException {
        collaboratorService.delete(id);
        return ResponseEntity.ok().build();
    }

}

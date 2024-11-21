package com.finalproject.portal.controller;

import com.finalproject.portal.dto.ComponentTypeDTO;
import com.finalproject.portal.service.ComponentTypeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portal Component Types", description = "Component Types Management Endpoints")
@RestController
@RequestMapping("/component-types")
@SecurityRequirement(name = "bearerAuth")
public class ComponentTypeController {

    private final ComponentTypeServiceImpl componentTypeServiceImpl;

    public ComponentTypeController(ComponentTypeServiceImpl componentTypeServiceImpl) {
        this.componentTypeServiceImpl = componentTypeServiceImpl;
    }

    @Operation(
            summary = "Gets all the Component Types",
            description = "Returns a paginated Component Types list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Types list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<ComponentTypeDTO>> getAll(Pageable pageable) {
        return componentTypeServiceImpl.getAll(pageable);
    }

    @Operation(
            summary = "Gets a Component Type by id",
            description = "Returns the Component Type found",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ComponentTypeDTO> getById(@PathVariable long id) {
        return componentTypeServiceImpl.getById(id);
    }

    @Operation(
            summary = "Creates a Component Type",
            description = "Returns the created Component Type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<ComponentTypeDTO> create(@RequestBody ComponentTypeDTO componentTypeDTO) {
        return componentTypeServiceImpl.create(componentTypeDTO);
    }

    @Operation(
            summary = "Updates a Component Type",
            description = "Returns the created Component Type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<ComponentTypeDTO> update(@RequestBody ComponentTypeDTO userCredentialsDTO) {
        return componentTypeServiceImpl.update(userCredentialsDTO);
    }

    @Operation(
            summary = "Deletes a Component Type",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type deleted successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        componentTypeServiceImpl.delete(id);
    }

}

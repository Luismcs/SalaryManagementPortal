package com.finalproject.portal.controller;

import com.finalproject.portal.dto.ComponentTypeDTO;
import com.finalproject.portal.dto.ComponentTypeSubtypeDTO;
import com.finalproject.portal.service.ComponentTypeSubtypeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portal Component Type SubTypes", description = "Component Type SubTypes Management Endpoints")
@RestController
@RequestMapping("/component-type-subtypes")
public class ComponentTypeSubtypeController {

    private final ComponentTypeSubtypeServiceImpl componentTypeSubtypeServiceImpl;

    public ComponentTypeSubtypeController(ComponentTypeSubtypeServiceImpl componentTypeSubtypeServiceImpl) {
        this.componentTypeSubtypeServiceImpl = componentTypeSubtypeServiceImpl;
    }

    @Operation(
            summary = "Gets all the Component Type Subtypes",
            description = "Returns a paginated Component Type Subtypes list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtypes list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<ComponentTypeSubtypeDTO>> getAll(Pageable pageable) {
        return componentTypeSubtypeServiceImpl.getAll(pageable);
    }

    @Operation(
            summary = "Gets a Component Type Subtype by id",
            description = "Returns the Component Type Subtype found",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ComponentTypeSubtypeDTO> getById(@PathVariable Long id) {
        return componentTypeSubtypeServiceImpl.getById(id);
    }

    @Operation(
            summary = "Creates a Component Type Subtype",
            description = "Returns the created Component Type Subtype",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping
    public ResponseEntity<ComponentTypeSubtypeDTO> create(@RequestBody @Valid ComponentTypeSubtypeDTO componentTypeSubtypeDTO) {
        return componentTypeSubtypeServiceImpl.create(componentTypeSubtypeDTO);
    }

    @Operation(
            summary = "Updates a Component Type Subtype",
            description = "Returns the created Component Type Subtype",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping
    public ResponseEntity<ComponentTypeSubtypeDTO> update(@RequestBody @Valid ComponentTypeSubtypeDTO componentTypeSubtypeDTO) {
        return componentTypeSubtypeServiceImpl.update(componentTypeSubtypeDTO);
    }

    @Operation(
            summary = "Deletes a Component Type Subtype",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype deleted successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        componentTypeSubtypeServiceImpl.delete(id);
        return ResponseEntity.ok().build();
    }

}

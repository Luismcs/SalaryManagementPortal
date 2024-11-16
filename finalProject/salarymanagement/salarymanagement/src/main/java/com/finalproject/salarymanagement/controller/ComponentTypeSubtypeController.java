package com.finalproject.salarymanagement.controller;

import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.service.impl.ComponentTypeSubtypeServiceImpl;
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

@Tag(name = "Component Type Subtypes", description = "Component Type Subtypes Management Endpoints")
@RestController
@RequestMapping("/component-type-subtypes")
public class ComponentTypeSubtypeController {

    private final ComponentTypeSubtypeServiceImpl componentTypeSubtypeService;

    public ComponentTypeSubtypeController(ComponentTypeSubtypeServiceImpl componentTypeSubtypeService) {
        this.componentTypeSubtypeService = componentTypeSubtypeService;
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
    public ResponseEntity<Page<ComponentTypeSubtypeDTO>> getAll(@ParameterObject
                                                                @PageableDefault(size = 20) Pageable pageable) {
        Page<ComponentTypeSubtypeDTO> page = componentTypeSubtypeService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Gets a Component Type Subtype by id",
            description = "Returns a Component Type Subtype",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ComponentTypeSubtypeDTO> getById(@PathVariable Long id) throws ComponentTypeSubtypeNotFoundException {
        ComponentTypeSubtypeDTO componentTypeSubtypeDTO = componentTypeSubtypeService.getById(id);
        return ResponseEntity.ok(componentTypeSubtypeDTO);
    }

    @Operation(
            summary = "Creates a Component Type Subtype",
            description = "Returns the created Component Type Subtype",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<ComponentTypeSubtypeDTO> create(@Valid @RequestBody ComponentTypeSubtypeDTO componentTypeSubtypeDTO) throws ComponentTypeNotFoundException {
        ComponentTypeSubtypeDTO createdComponentTypeSubtypeDTO =
                componentTypeSubtypeService.create(componentTypeSubtypeDTO);
        return ResponseEntity.ok(createdComponentTypeSubtypeDTO);
    }

    @Operation(
            summary = "Updates a Component Type Subtype",
            description = "Returns the updated Component Type Subtype",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<ComponentTypeSubtypeDTO> update(@RequestBody ComponentTypeSubtypeDTO componentTypeSubtypeDTO) throws ComponentTypeNotFoundException {
        ComponentTypeSubtypeDTO createdComponentTypeSubtypeDTO =
                componentTypeSubtypeService.update(componentTypeSubtypeDTO);
        return ResponseEntity.ok(createdComponentTypeSubtypeDTO);
    }

    @Operation(
            summary = "Deletes a Component Type Subtype",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type Subtype deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeSubtypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ComponentTypeSubtypeNotFoundException {
        componentTypeSubtypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}

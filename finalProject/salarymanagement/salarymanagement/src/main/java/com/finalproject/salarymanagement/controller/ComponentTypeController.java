package com.finalproject.salarymanagement.controller;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.service.impl.ComponentTypeServiceImpl;
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

@Tag(name = "Component Types", description = "Component Types Management Endpoints")
@RestController
@RequestMapping("/component-types")
public class ComponentTypeController {

    private final ComponentTypeServiceImpl componentTypeService;

    public ComponentTypeController(ComponentTypeServiceImpl componentTypeService) {
        this.componentTypeService = componentTypeService;
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
    public ResponseEntity<Page<ComponentTypeDTO>> getAll(@ParameterObject
                                                         @PageableDefault(size = 20) Pageable pageable) {
        Page<ComponentTypeDTO> page = componentTypeService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Gets a Component Type by id",
            description = "Returns a Component Type ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ComponentTypeDTO> getById(@PathVariable Long id) throws ComponentTypeNotFoundException {
        return ResponseEntity.ok(componentTypeService.getById(id));
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
    public ResponseEntity<ComponentTypeDTO> create(@Valid @RequestBody ComponentTypeDTO componentTypeDTO) {
        ComponentTypeDTO createdComponentTypeDTO = componentTypeService.create(componentTypeDTO);
        return ResponseEntity.ok(createdComponentTypeDTO);
    }

    @PutMapping()
    public ResponseEntity<ComponentTypeDTO> update(@RequestBody ComponentTypeDTO componentTypeDTO) throws ComponentTypeNotFoundException {
        ComponentTypeDTO createdComponentTypeDTO = componentTypeService.update(componentTypeDTO);
        return ResponseEntity.ok(createdComponentTypeDTO);
    }

    @Operation(
            summary = "Deletes a Component Type",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Component Type deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ComponentTypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ComponentTypeNotFoundException {
        componentTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

}

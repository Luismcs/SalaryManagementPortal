package com.finalproject.salarymanagement.controller;

import com.finalproject.salarymanagement.dto.SalaryComponentDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.SalaryComponentNotFoundException;
import com.finalproject.salarymanagement.service.impl.SalaryComponentServiceImpl;
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

@Tag(name = "Salary Components", description = "Salary Components Management Endpoints")
@RestController
@RequestMapping("/salary-components")
public class SalaryComponentController {

    private final SalaryComponentServiceImpl salaryComponentService;

    SalaryComponentController(SalaryComponentServiceImpl salaryComponentService) {
        this.salaryComponentService = salaryComponentService;
    }

    @Operation(
            summary = "Gets all the Salary Components",
            description = "Returns a paginated Salary Components list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Components list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<SalaryComponentDTO>> getAll(@ParameterObject
                                                           @PageableDefault(size = 20) Pageable pageable) {
        Page<SalaryComponentDTO> page = salaryComponentService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Gets a Salary Component by id",
            description = "Returns a Salary Component",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary Component not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SalaryComponentDTO> getById(@PathVariable Long id) throws SalaryComponentNotFoundException {
        SalaryComponentDTO salaryComponentDTO = salaryComponentService.getById(id);
        return ResponseEntity.ok(salaryComponentDTO);
    }

    @Operation(
            summary = "Updates a Salary Component",
            description = "Returns the updated Salary Component",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<SalaryComponentDTO> update(@Valid @RequestBody SalaryComponentDTO salaryComponentDTO) throws ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException {
        SalaryComponentDTO createdSalaryComponentDTO = salaryComponentService.update(salaryComponentDTO);
        return ResponseEntity.ok(createdSalaryComponentDTO);
    }

    @Operation(
            summary = "Creates a Salary Component",
            description = "Returns the created Salary Component",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<SalaryComponentDTO> create(@Valid @RequestBody SalaryComponentDTO salaryComponentDTO) throws ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException {
        SalaryComponentDTO createdSalaryComponentDTO = salaryComponentService.create(salaryComponentDTO);
        return ResponseEntity.ok(createdSalaryComponentDTO);
    }

    @Operation(
            summary = "Deletes a Salary Component",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary Component not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws SalaryComponentNotFoundException {
        salaryComponentService.delete(id);
        return ResponseEntity.ok().build();
    }

}

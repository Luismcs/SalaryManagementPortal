package com.finalproject.salarymanagement.controller;

import com.finalproject.salarymanagement.dto.SalaryDTO;
import com.finalproject.salarymanagement.exception.*;
import com.finalproject.salarymanagement.service.impl.SalaryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryServiceImpl salaryService;

    SalaryController(SalaryServiceImpl salaryService) {
        this.salaryService = salaryService;
    }

    @Operation(
            summary = "Gets all the Salaries",
            description = "Returns a paginated Salary list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<SalaryDTO>> getAll(@ParameterObject
                                                  @PageableDefault(size = 20) Pageable pageable) {
        Page<SalaryDTO> page = salaryService.getAll(pageable);
        return ResponseEntity.ok(page);
    }


    @Operation(
            summary = "Gets a Salary by id",
            description = "Returns a Salary",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SalaryDTO> getById(@PathVariable Long id) throws SalaryNotFoundException {
        SalaryDTO salaryDTO = salaryService.getById(id);
        return ResponseEntity.ok(salaryDTO);
    }

    @Operation(
            summary = "Approves a Salary by id",
            description = "Returns a Salary approves",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary approved and returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/approve/{id}")
    public ResponseEntity<SalaryDTO> approve(@PathVariable Long id) throws SalaryNotFoundException, SalaryAlreadyApprovedException, SalaryCannotBeApprovedException, SalaryAlreadyActiveException {
        SalaryDTO salaryDTO = salaryService.approve(id);
        return ResponseEntity.ok(salaryDTO);
    }

    @Operation(
            summary = "Creates a Salary",
            description = "Returns the created Salary",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<SalaryDTO> create(@Valid @RequestBody SalaryDTO salaryDTO) throws ComponentTypeNotFoundException, ComponentTypeSubtypeNotFoundException, DuplicateSalaryForImplementationDateException {
        SalaryDTO createdSalaryComponentDTO = salaryService.create(salaryDTO);
        return ResponseEntity.ok(createdSalaryComponentDTO);
    }

    @Operation(
            summary = "Updates a Salary",
            description = "Returns the updated Salary",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Component Type not found"),
                    @ApiResponse(responseCode = "404", description = "Component Type Subtype not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<SalaryDTO> update(@Valid @RequestBody SalaryDTO salaryDTO) throws SalaryNotFoundException, ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException, SalaryCannotBeUpdatedException, SalaryAlreadyActiveException, DuplicateSalaryForImplementationDateException {
        SalaryDTO createdSalaryComponentDTO = salaryService.update(salaryDTO);
        return ResponseEntity.ok(createdSalaryComponentDTO);
    }


    @Operation(
            summary = "Deletes a Salary",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws SalaryNotFoundException {
        salaryService.delete(id);
        return ResponseEntity.ok().build();
    }


}

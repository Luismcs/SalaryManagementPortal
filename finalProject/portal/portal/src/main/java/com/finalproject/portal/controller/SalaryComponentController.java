package com.finalproject.portal.controller;

import com.finalproject.portal.dto.SalaryComponentDTO;
import com.finalproject.portal.service.SalaryComponentServiceImpl;
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

@Tag(name = "Portal Salary Components", description = "Salary Components Management Endpoints")
@RestController
@RequestMapping("/salary-components")
@SecurityRequirement(name = "bearerAuth")
public class SalaryComponentController {

    private final SalaryComponentServiceImpl salaryComponentService;

    public SalaryComponentController(SalaryComponentServiceImpl salaryComponentService) {
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
    public ResponseEntity<Page<SalaryComponentDTO>> getAll(Pageable pageable) {
        return salaryComponentService.getAll(pageable);
    }

    @Operation(
            summary = "Gets a Salary Component by id",
            description = "Returns the Salary Component found",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary Component not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SalaryComponentDTO> getById(@PathVariable long id) {
        return salaryComponentService.getById(id);
    }

    @Operation(
            summary = "Creates a Salary Component",
            description = "Returns the created Salary Component",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<SalaryComponentDTO> create(@RequestBody @Valid SalaryComponentDTO salaryComponentDTO) {
        return salaryComponentService.create(salaryComponentDTO);
    }

    @Operation(
            summary = "Updates a Salary Component",
            description = "Returns the updated Salary Component",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryComponentDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<SalaryComponentDTO> update(@RequestBody @Valid SalaryComponentDTO salaryComponentDTO) {
        return salaryComponentService.update(salaryComponentDTO);
    }

    @Operation(
            summary = "Deletes a Salary Component",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary Component deleted successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Salary Component not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        salaryComponentService.delete(id);
        return ResponseEntity.ok().build();
    }

}

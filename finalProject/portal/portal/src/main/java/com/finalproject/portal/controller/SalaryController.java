package com.finalproject.portal.controller;

import com.finalproject.portal.dto.SalaryComponentDTO;
import com.finalproject.portal.dto.SalaryDTO;
import com.finalproject.portal.service.SalaryComponentServiceImpl;
import com.finalproject.portal.service.SalaryServiceImpl;
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

@Tag(name = "Portal Salary", description = "Salary Management Endpoints")
@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryServiceImpl salaryService;

    public SalaryController(SalaryServiceImpl salaryService) {
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
    public ResponseEntity<Page<SalaryDTO>> getAll(Pageable pageable) {
        return salaryService.getAll(pageable);
    }

    @Operation(
            summary = "Gets a Salary by id",
            description = "Returns the Salary found",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Salary not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SalaryDTO> getById(@PathVariable long id) {
        return salaryService.getById(id);
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
    public ResponseEntity<SalaryDTO> approve(@PathVariable Long id) {
        return salaryService.approve(id);
    }

    @Operation(
            summary = "Creates a Salary",
            description = "Returns the created Salary",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<SalaryDTO> create(@RequestBody @Valid SalaryDTO salaryDTO) {
        return salaryService.create(salaryDTO);
    }

    @Operation(
            summary = "Updates a Salary",
            description = "Returns the updated Salary",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalaryDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<SalaryDTO> update(@RequestBody @Valid SalaryDTO salaryDTO) {
        return salaryService.update(salaryDTO);
    }

    @Operation(
            summary = "Deletes a Salary",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary deleted successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Salary not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        salaryService.delete(id);
        return ResponseEntity.ok().build();
    }

}

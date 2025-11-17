package com.finalproject.collaborator.controller;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.exception.AddressNotFoundException;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.service.impl.AddressServiceImpl;
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

@Tag(name = "Addresses", description = "Address Management Endpoints")
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressServiceImpl addressService;

    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @Operation(
            summary = "Gets all the addresses",
            description = "Returns a paginated address list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<AddressDTO>> getAll(@ParameterObject
                                                   @PageableDefault(size = 20) Pageable pageable) {
        Page<AddressDTO> page = addressService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Gets a address by id",
            description = "Returns a address ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AddressDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Address not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable long id) throws AddressNotFoundException {
        AddressDTO foundCollaboratorDTO = addressService.getById(id);
        return ResponseEntity.ok(foundCollaboratorDTO);
    }

    @Operation(
            summary = "Creates a address",
            description = "Returns the created address",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AddressDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<AddressDTO> create(@Valid @RequestBody AddressDTO addressDTO) throws CollaboratorNotFoundException {
        AddressDTO savedCollaboratorDTO = addressService.add(addressDTO);
        return ResponseEntity.ok(savedCollaboratorDTO);
    }

    @Operation(
            summary = "Updates a address",
            description = "Returns the updated address",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AddressDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Address not found"),
                    @ApiResponse(responseCode = "404", description = "Collaborator not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<AddressDTO> update(@Valid @RequestBody AddressDTO addressDTO) throws CollaboratorNotFoundException, AddressNotFoundException {
        AddressDTO savedCollaboratorDTO = addressService.update(addressDTO);
        return ResponseEntity.ok(savedCollaboratorDTO);
    }

    @Operation(
            summary = "Deletes a address",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AddressDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Address not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDTO> delete(@PathVariable long id) throws AddressNotFoundException {
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }

}

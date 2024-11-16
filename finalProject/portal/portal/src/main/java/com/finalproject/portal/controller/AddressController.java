package com.finalproject.portal.controller;

import com.finalproject.portal.dto.AddressDTO;
import com.finalproject.portal.service.AddressServiceImpl;
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

@Tag(name = "Portal Addresses", description = "Address Management Endpoints")
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressServiceImpl addressServiceImpl;

    public AddressController(AddressServiceImpl addressServiceImpl) {
        this.addressServiceImpl = addressServiceImpl;
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
    public ResponseEntity<Page<AddressDTO>> getAll(Pageable pageable) {
        return addressServiceImpl.getAll(pageable);
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
    public ResponseEntity<AddressDTO> getById(@PathVariable long id) {
        return addressServiceImpl.getById(id);
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
    public ResponseEntity<AddressDTO> create(@Valid @RequestBody AddressDTO addressDTO) {
        return addressServiceImpl.create(addressDTO);
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
    public ResponseEntity<AddressDTO> update(@Valid @RequestBody AddressDTO addressDTO) {
        return addressServiceImpl.update(addressDTO);
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
    public ResponseEntity<Void> delete(@PathVariable long id) {
        addressServiceImpl.delete(id);
        return ResponseEntity.ok().build();
    }

}

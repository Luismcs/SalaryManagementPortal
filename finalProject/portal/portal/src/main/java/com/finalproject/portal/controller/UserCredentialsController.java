package com.finalproject.portal.controller;

import com.finalproject.portal.dto.UserCredentialsRequestDTO;
import com.finalproject.portal.dto.UserCredentialsResponseDTO;
import com.finalproject.portal.service.UserCredentialsServiceImpl;
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

@Tag(name = "Portal User Credentials", description = "User Credentials Management Endpoints")
@RestController
@RequestMapping("/user-credentials")
@SecurityRequirement(name = "bearerAuth")
public class UserCredentialsController {

    private final UserCredentialsServiceImpl userCredentialsService;

    public UserCredentialsController(UserCredentialsServiceImpl userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    @Operation(
            summary = "Gets all the user credentials",
            description = "Returns a paginated user credentials list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials list returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<UserCredentialsResponseDTO>> getAll(Pageable pageable) {
        return userCredentialsService.getAll(pageable);
    }

    @Operation(
            summary = "Gets a user Credentials by id",
            description = "Returns the found user Credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User Credentials not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserCredentialsResponseDTO> getById(@PathVariable long id) {
        return userCredentialsService.getById(id);
    }

    @Operation(
            summary = "Creates user credentials",
            description = "Returns the collaborator created",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<UserCredentialsResponseDTO> create(@RequestBody
                                                             UserCredentialsRequestDTO userCredentialsRequestDTO) {
        return userCredentialsService.create(userCredentialsRequestDTO);
    }

    @Operation(
            summary = "Updates a user Credentials",
            description = "Returns the updated user Credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Role Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<UserCredentialsResponseDTO> update(@Valid @RequestBody
                                                             UserCredentialsRequestDTO userCredentialsRequestDTO) {
        return userCredentialsService.update(userCredentialsRequestDTO);
    }

    @Operation(
            summary = "Deletes a user Credentials",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials deleted successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "User Credentials not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userCredentialsService.delete(id);
    }

    @Operation(
            summary = "Verifies if username already exists in userCredentials data",
            description = "Returns boolean",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Response returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/exists/username/{username}")
    public void existsByUsername(@PathVariable String username) {
        userCredentialsService.existsByUsername(username);
    }

}

package com.finalproject.authentication.controller;

import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import com.finalproject.authentication.service.impl.UserCredentialsServiceImpl;
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
@RequestMapping("/user-credentials")
public class UserCredentialsController {

    private final UserCredentialsServiceImpl userCredentialsServiceImpl;

    public UserCredentialsController(UserCredentialsServiceImpl userCredentialsServiceImpl) {
        this.userCredentialsServiceImpl = userCredentialsServiceImpl;
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
    public ResponseEntity<Page<UserCredentialsDTO>> getAll(@ParameterObject
                                                           @PageableDefault(size = 20) Pageable pageable) {
        Page<UserCredentialsDTO> page = userCredentialsServiceImpl.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Gets a user Credentials by id",
            description = "Returns the found user Credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User Credentials not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserCredentialsDTO> getById(@PathVariable long id) throws UserCredentialsNotFound {
        return ResponseEntity.ok(userCredentialsServiceImpl.getById(id));
    }

    @Operation(
            summary = "Creates user credentials",
            description = "Returns the collaborator created",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping()
    public ResponseEntity<UserCredentialsDTO> create(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO) {
        UserCredentialsDTO savedCollaboratorDTO = userCredentialsServiceImpl.create(userCredentialsDTO);
        return ResponseEntity.ok(savedCollaboratorDTO);
    }

    @PutMapping()
    public ResponseEntity<UserCredentialsDTO> update(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO)
            throws UserCredentialsNotFound {
        UserCredentialsDTO updatedCollaboratorDTO = userCredentialsServiceImpl.update(userCredentialsDTO);
        return ResponseEntity.ok(updatedCollaboratorDTO);
    }

    @Operation(
            summary = "Deletes user credentials",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws UserCredentialsNotFound {
        userCredentialsServiceImpl.delete(id);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userCredentialsServiceImpl.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

}

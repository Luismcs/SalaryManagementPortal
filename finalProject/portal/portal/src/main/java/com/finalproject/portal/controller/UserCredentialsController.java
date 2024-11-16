package com.finalproject.portal.controller;


import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.UserCredentialsDTO;
import com.finalproject.portal.service.UserCredentialsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portal User Credentials", description = "User Credentials Management Endpoints")
@RestController
@RequestMapping("/user-credentials")
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
    public ResponseEntity<Page<UserCredentialsDTO>> getAll(Pageable pageable) {
        return userCredentialsService.getAll(pageable);
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
    public ResponseEntity<UserCredentialsDTO> getById(@PathVariable long id) {
        return userCredentialsService.getById(id);
    }

    @Operation(
            summary = "Creates a user Credentials",
            description = "Returns the created user Credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Credentials created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserCredentialsDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping()
    public ResponseEntity<UserCredentialsDTO> update(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        return userCredentialsService.update(userCredentialsDTO);
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

}

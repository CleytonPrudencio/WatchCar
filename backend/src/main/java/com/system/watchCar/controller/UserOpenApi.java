package com.system.watchCar.controller;

import com.system.watchCar.dto.exceptions.ErrorDTO;
import com.system.watchCar.dto.requests.UserGestorRequest;
import com.system.watchCar.dto.response.UserSimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

public interface UserOpenApi {

    @Tag(name = "Usuário", description = "Operações relacionadas ao usuário")
    @Operation(
            description = "Create a new user and save it to the database",
            summary = "Register a new user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User  registered successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserSimpleResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)
            )),
            @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    ResponseEntity<UserSimpleResponse> register(@RequestBody(
            description = "Dados do usuário a ser criado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserGestorRequest.class))
    ) UserGestorRequest request);
}

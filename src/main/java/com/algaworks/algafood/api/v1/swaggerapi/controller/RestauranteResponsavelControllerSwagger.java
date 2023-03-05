package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.output.UsuarioModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteResponsavelControllerSwagger {

        @Operation(summary = "Lista os usuários responsáveis associados a restaurante", responses = {
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public CollectionModel<UsuarioModel> responsavels(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId);

        @Operation(summary = "Associação de restaurante com usuário responsáve", responses = {
                        @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public ResponseEntity<Void> associar(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                        @Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

        @Operation(summary = "Desassociação de restaurante com usuário responsável", responses = {
                        @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public ResponseEntity<Void> desassociar(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                        @Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);
}

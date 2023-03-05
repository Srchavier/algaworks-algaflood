package com.algaworks.algafood.api.v1.swaggerapi.controller;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestHeader;

import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.model.output.FotoProdutoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos")
public interface RestauranteProdutoFotoControllerSwagger {

        @Operation(summary = "Busca a foto do produto de um restaurante", responses = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
                                        @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                                        @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
                        }),
                        @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                                        @Content(schema = @Schema(ref = "Problema")) }),
                        @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = {
                                        @Content(schema = @Schema(ref = "Problema")) }),

        })
        public FotoProdutoModel buscar(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

        @Operation(hidden = true)
        public ResponseEntity<InputStreamResource> servir(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId,
                        @RequestHeader(name = "accept") String acceptHearder)
                        throws HttpMediaTypeNotAcceptableException;

        @Operation(summary = "Atualizar foto do produto de um restaurante", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public FotoProdutoModel atualizarFoto(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId,
                        @RequestBody(required = true) FotoProdutoInput fotoProdutoInput) throws IOException;

        @Operation(summary = "Exclui a foto do produto de um restauranteD", responses = {
                        @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
                        @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void delete(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);
}

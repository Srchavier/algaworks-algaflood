package com.algaworks.algafood.api.swaggerapi.controller;

import java.util.List;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.model.output.CidadeModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cidades", description = "Gerencia as cidades")
public interface CidadeControllerSwagger {

        @Operation(summary = "Listar todas as cidades")
        public List<CidadeModel> listar();

        @Operation(summary = "Buscar um cidades por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public CidadeModel buscar(
                        @Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);

        @Operation(summary = "Cadastrar um cidade")
        public CidadeModel adicionar(
                        @RequestBody(description = "Representação de uma nova cidade", required = true) CidadeInput cidade);

        @Operation(summary = "Atualizado uma cidade por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public CidadeModel atualizar(
                        @Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId,
                        @RequestBody(description = "Atualização de uma cidade", required = true) CidadeInput cidadeInput);

        @Operation(summary = "Excluir uma cidade por ID", responses = {
                        @ApiResponse(responseCode = "204"),
                        @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void remover(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);
}

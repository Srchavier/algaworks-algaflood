package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.model.output.EstadoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estados", description = "Gerencia os estados")
public interface EstadoControllerSwagger {

        @Operation(summary = "Lista de estados")
        public CollectionModel<EstadoModel> listar();

        @Operation(summary = "Buscar de estado por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID estado inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public EstadoModel buscar(
                        @Parameter(description = "ID do estado", example = "1", required = true) Long estadoId);

        @Operation(summary = "Cadastrar um estado", responses = {
                        @ApiResponse(responseCode = "201", description = "Estado cadastrada") })
        public EstadoModel adicionar(
                        @RequestBody(description = "Representação de um novo estado", required = true) EstadoInput estado);

        @Operation(summary = "Atualizar de estado por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "Id estado inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
        })
        public EstadoModel atualizar(
                        @Parameter(description = "ID do estado", example = "1", required = true) Long estadoId,
                        @RequestBody(description = "Representação de um estado com os novos dados", required = true) EstadoInput estadoInput);

        @Operation(summary = "Excluir de estado por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Removido com sucesso"),
                        @ApiResponse(responseCode = "400", description = "ID estado inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
        })
        public void remover(@Parameter(description = "ID do estado", required = true) Long estadoId);

}

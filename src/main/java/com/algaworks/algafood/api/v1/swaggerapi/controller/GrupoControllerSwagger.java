package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.model.output.GrupoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Grupos", description = "Gerencia os grupos de usúario")
public interface GrupoControllerSwagger {

        @Operation(summary = "Listar os grupos")
        public CollectionModel<GrupoModel> listar();

        @Operation(summary = "Buscar um grupo por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "Id do grupo inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Grupo não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))

        })
        public GrupoModel grupoPorId(
                        @Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId);

        @Operation(summary = "Cadastrar um grupo")
        public GrupoModel adicionar(
                        @RequestBody(description = "Representação de um novo grupo", required = true) GrupoInput input);

        @Operation(summary = "Atualizar um grupo por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "Id do grupo inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Grupo não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public GrupoModel atualizar(
                        @Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId,
                        @RequestBody(description = "Representação de um atualização de uma cidade", required = true) GrupoInput input);

        @Operation(summary = "Exclui um  grupo por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "grupo excluído"),
                        @ApiResponse(responseCode = "400", description = "Id do grupo inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Grupo não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void deletar(@Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId);

}

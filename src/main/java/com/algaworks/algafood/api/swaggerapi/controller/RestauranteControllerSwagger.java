package com.algaworks.algafood.api.swaggerapi.controller;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.output.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.output.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.output.RestauranteModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes", description = "Gerencia restaurante")
public interface RestauranteControllerSwagger {
        @Operation(summary = "Lista restaurantes", parameters = {
                        @Parameter(name = "projecao", description = "Projeção dos restaurantes por nome", example = "apenas-nome", in = ParameterIn.QUERY, required = false)
        })
        public List<RestauranteBasicoModel> listar();

        @Operation(hidden = true)
        public List<RestauranteApenasNomeModel> listarApenasNomes();

        @Operation(summary = "Buscar um restaurante por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "estaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public RestauranteModel buscar(
                        @Parameter(description = "Id restaurante", example = "1", required = true) Long restauranteId);

        @Operation(summary = "Adicionar um restaurante")
        public RestauranteModel adicionar(
                        @RequestBody(description = "Represetação de um restaurante", required = true) RestauranteInput restauranteInput);

        @Operation(summary = "Atualizado um restaurante por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public RestauranteModel atualizar(
                        @Parameter(description = "Id restaurante", example = "1", required = true) Long restauranteId,
                        @RequestBody(description = "Represetação de um restaurante", required = true) RestauranteInput restauranteInput);

        @Operation(summary = "Abrir um restaurante", responses = {
                        @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void aberto(
                        @Parameter(description = "Id restaurante", example = "1", required = true) Long restauranteId);

        @Operation(summary = "Fechar um restaurante do ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void fechamento(
                        @Parameter(description = "Id restaurante", example = "1", required = true) Long restauranteId);

        @Operation(summary = "Ativa múltiplos restaurante", responses = {
                        @ApiResponse(responseCode = "204", description = "Restaurante ativo com sucesso"),
        })
        public void ativaMultiplas(
                        @RequestBody(description = "Represetação de multiplos restaurante ID", required = true) List<Long> restauranteIds);

        @Operation(summary = "Inativa múltiplos restaurantes", responses = {
                        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
        })
        public void inativaMultiplas(
                        @RequestBody(description = "Represetação de multiplos restaurante ID", required = true) List<Long> restauranteIds);

        @Operation(summary = "Ativa um restaurante por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void ativa(
                        @Parameter(description = "Id restaurante", example = "1", required = true) Long restauranteId);

        @Operation(summary = "Inativa um restaurante por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void inativa(
                        @Parameter(description = "Id restaurante", example = "1", required = true) Long restauranteId);

        @Operation(summary = "Buscar restaurante por taxa frente")
        public List<RestauranteModel> restaurantesPorTaxaFrete(
                        @Parameter(description = "Nome restaurante", example = "s", required = true) String nome,
                        @Parameter(description = "Id restaurante", example = "1", required = true) BigDecimal taxaInicial,
                        @Parameter(description = "Nome restaurante", example = "10", required = true) BigDecimal taxaFinal);

        @Operation(summary = "Buscar restaurante por taxa frente gratis")
        public List<RestauranteModel> restaurantesPorFreteGratis(
                        @Parameter(description = "Nome restaurante", example = "s", required = true) String nome);

        @Operation(summary = "Buscar primeiro restaurante")
        public RestauranteModel restaurantesPrimeiro();
}

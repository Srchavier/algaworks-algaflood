package com.algaworks.algafood.api.swaggerapi.controller;

import java.util.List;

import com.algaworks.algafood.api.model.output.FormaPagamentoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteFormaPagamentoControllerSwagger {

        @Operation(summary = "Listar as formas de  pagamento associadas a restaurante")
        public List<FormaPagamentoModel> listar(
                        @Parameter(description = "ID restaurante", required = true, example = "1") Long restauranteId);

        @Operation(summary = "Associação de restaurante com forma de pagamento", responses = {
                        @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public void associarFormaPagamento(
                        @Parameter(description = "ID restaurante", required = true, example = "1") Long restauranteId,
                        @Parameter(description = "ID forma pagamento", required = true, example = "1") Long formaPagamentoId);

        @Operation(summary = "Desassociação de restaurante com forma de pagamento", responses = {
                        @ApiResponse(responseCode = "104", description = "Desassociação realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public void desassociarFormaPagamento(
                        @Parameter(description = "ID restaurante", required = true, example = "1") Long restauranteId,
                        @Parameter(description = "ID forma pagamento", required = true, example = "1") Long formaPagamentoId);
}

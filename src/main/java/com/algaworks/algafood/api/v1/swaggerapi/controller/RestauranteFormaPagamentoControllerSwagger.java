package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.output.FormaPagamentoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteFormaPagamentoControllerSwagger {

        @Operation(summary = "Listar as formas de  pagamento associadas a restaurante")
        public CollectionModel<FormaPagamentoModel> listar(
                        @Parameter(description = "ID restaurante", required = true, example = "1") Long restauranteId);

        @Operation(summary = "Associação de restaurante com forma de pagamento", responses = {
                        @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public ResponseEntity<Void> associarFormaPagamento(
                        @Parameter(description = "ID restaurante", required = true, example = "1") Long restauranteId,
                        @Parameter(description = "ID forma pagamento", required = true, example = "1") Long formaPagamentoId);

        @Operation(summary = "Desassociação de restaurante com forma de pagamento", responses = {
                        @ApiResponse(responseCode = "104", description = "Desassociação realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) })
        })
        public ResponseEntity<Void> desassociarFormaPagamento(
                        @Parameter(description = "ID restaurante", required = true, example = "1") Long restauranteId,
                        @Parameter(description = "ID forma pagamento", required = true, example = "1") Long formaPagamentoId);
}

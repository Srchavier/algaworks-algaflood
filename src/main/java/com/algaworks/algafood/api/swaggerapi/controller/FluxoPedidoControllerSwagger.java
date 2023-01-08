package com.algaworks.algafood.api.swaggerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos")
public interface FluxoPedidoControllerSwagger {
        @Operation(summary = "Confirmação de pedido", responses = {
                        @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) }),
        })
        public void confirmar(
                        @Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

        @Operation(summary = "Registrar entrega de pedido", responses = {
                        @ApiResponse(responseCode = "204", description = "Pedido entregue com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) }),
        })
        public void entregue(
                        @Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

        @Operation(summary = "Cancelmento do pedido", responses = {
                        @ApiResponse(responseCode = "204", description = "Pedido cencelado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) }),
        })
        public void cancelado(
                        @Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);
}

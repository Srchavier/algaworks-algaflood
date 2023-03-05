package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.model.output.PedidoModel;
import com.algaworks.algafood.api.v1.model.output.PedidoResumoModel;
import com.algaworks.algafood.api.v1.swaggerapi.model.PageableParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos")
public interface PedidoControllerSwagger {

        @Operation(summary = "Pesquisa de pedidos", parameters = {
                        @Parameter(in = ParameterIn.QUERY, name = "clienteId", description = "ID do cliente para filtro da pesquisa", example = "1", schema = @Schema(type = "integer")),
                        @Parameter(in = ParameterIn.QUERY, name = "restauranteId", description = "ID do restaurante para filtro da pesquisa", example = "1", schema = @Schema(type = "integer")),
                        @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoInicio", description = "Data/hora de criação inicial para filtro da pesquisa", example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
                        @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoFim", description = "Data/hora de criação final para filtro da pesquisa", example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time")),
                        @Parameter(in = ParameterIn.QUERY, name = "campos", description = "Nomes das propriedades para filtrar na resposta, separado por vírgulas", example = "codigo,subtotal", schema = @Schema(type = "string"))

        })
        @PageableParameter
        public PagedModel<PedidoResumoModel> todosPedidos(@Parameter(hidden = true) PedidoFilter filtro,
                        @Parameter(hidden = true) Pageable pageable);

        @Operation(summary = "Busca um pedido por código", parameters = {
                        @Parameter(in = ParameterIn.QUERY, name = "campos", description = "Nomes das propriedades para filtrar na resposta, separado por vírgulas", example = "codigo,subtotal", schema = @Schema(type = "string"))
        }, responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                                        @Content(schema = @Schema(ref = "Problema")) }),

        })
        public PedidoModel pedido(
                        @Parameter(description = "Código de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002", required = true) String codigoPedido);

        @Operation(summary = "Registra um pedido", responses = {
                        @ApiResponse(responseCode = "201", description = "Pedido registrado"),
        })
        public PedidoModel adicionar(@RequestBody(description = "Represetação de um pedido") PedidoInput pedidoInput);
}

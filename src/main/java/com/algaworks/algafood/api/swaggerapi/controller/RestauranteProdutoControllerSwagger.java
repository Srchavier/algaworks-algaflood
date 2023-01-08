package com.algaworks.algafood.api.swaggerapi.controller;

import java.util.List;

import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.model.output.ProdutoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos", description = "Gerencia os produtos de restaurantes")
public interface RestauranteProdutoControllerSwagger {
        @Operation(summary = "Lista os produtos de um restaurante", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public List<ProdutoModel> buscarTodosProdutos(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long idRestaurante,
                        @Parameter(description = "Indica se deve ou não incluir produtos inativos no resultado da listagem", example = "false") boolean incluirInativos);

        @Operation(summary = "Busca um produto de um restaurante", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public ProdutoModel buscarProdutoPorId(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long idRestaurante,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long idProduto);

        @Operation(summary = "Cadastra um produto de um restaurante", responses = {
                        @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public ProdutoModel salvaProduto(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long idRestaurante,
                        @RequestBody(description = "Representação de um novo produto", required = true) ProdutoInput produtoInput);

        @Operation(summary = "Atualiza um produto de um restaurante", responses = {
                        @ApiResponse(responseCode = "200", description = "Produto atualizado"),
                        @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public ProdutoModel alterarProduto(
                        @Parameter(description = "ID do restaurante", example = "1", required = true) Long idRestaurante,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long idProduto,
                        @RequestBody(description = "Representação de um produto com os novos dados", required = true) ProdutoInput produtoInput);

        @Operation(summary = "Exclui um produto por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "produto excluído"),
                        @ApiResponse(responseCode = "400", description = "Id do produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void deletaProduto(
                        @Parameter(description = "ID do produto", example = "1", required = true) Long idProduto);
}

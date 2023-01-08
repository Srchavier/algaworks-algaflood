package com.algaworks.algafood.api.swaggerapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.model.output.FormaPagamentoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Formas de pagamento", description = "Gerencia forma de pagamentos")
public interface FormaPagamentoControllerSwagger {

        @Operation(summary = "Lista forma de pagamento")
        public ResponseEntity<List<FormaPagamentoModel>> listar(@Parameter(hidden = true) ServletWebRequest request);

        @Operation(summary = "Busca uma forma pagamento por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
        })
        public ResponseEntity<FormaPagamentoModel> buscar(
                        @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
                        @Parameter(hidden = true) ServletWebRequest request);

        @Operation(summary = "Cadastra uma forma de pagamento", responses = {
                        @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"),
        })
        public FormaPagamentoModel adicionar(
                        @RequestBody(description = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);

        @Operation(summary = "Atualiza uma forma de pagamento por ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        public FormaPagamentoModel atualizar(
                        @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
                        @RequestBody(description = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);

        @Operation(summary = "Exclui uma forma de pagamento por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
        })
        public void remover(
                        @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}

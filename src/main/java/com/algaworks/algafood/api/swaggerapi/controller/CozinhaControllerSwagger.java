package com.algaworks.algafood.api.swaggerapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.model.output.CozinhaModel;
import com.algaworks.algafood.api.swaggerapi.model.PageableParameter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cozinhas", description = "Gerenciamento de cozinhas")
public interface CozinhaControllerSwagger {

	@PageableParameter
	@Operation(summary = "Lista as cozinhas com paginação")
	public Page<CozinhaModel> listar(@Parameter(hidden = true) Pageable pageable);

	@Operation(summary = "Busca uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	public CozinhaModel buscar(
			@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

	@Operation(summary = "Cadastra uma cozinha", responses = {
			@ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
	})
	public CozinhaModel adicionar(
			@RequestBody(description = "Representação de uma cozinha com os novos dados", required = true) CozinhaInput cozinhaInput);

	@Operation(summary = "Atualiza uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(ref = "Problema"))),
	})
	public CozinhaModel atualizar(
			@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long cozinhaId,
			@RequestBody(description = "Representação de uma cozinha com os novos dados", required = true) CozinhaInput cozinhaInput);

	@Operation(summary = "Exclui uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Cozinha excluída"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	public void remover(@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);
}

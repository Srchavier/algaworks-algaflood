package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.output.UsuarioModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Gerenciar Usuários")
public interface UsuarioControllerSwagger {

        @Operation(summary = "Listar Usuários")
        public CollectionModel<UsuarioModel> listar();

        @Operation(summary = "Buscar usuário por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public UsuarioModel buscarPorId(
                        @Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

        @Operation(summary = "Adicionar usuário", responses = {
                        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso") })
        public UsuarioModel salvar(
                        @RequestBody(description = "Representação de um novo usuário") UsuarioComSenhaInput usuarioPrimeiroCadastro);

        @Operation(summary = "Atualiza um usuário por ID", responses = {
                        @ApiResponse(responseCode = "201", description = "Usuário atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public UsuarioModel alterar(
                        @Parameter(description = "Id usuário", example = "1", required = true) Long usuarioId,
                        @RequestBody(description = "Representação novos dados do usuário", required = true) UsuarioInput usuarioInput);

        @Operation(summary = "Atualiza a senha de um usuário", responses = {
                        @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
        })
        public void alterar(@Parameter(description = "Id usuário", example = "1", required = true) Long usuarioId,
                        @RequestBody(description = "Representação de uma nova senha do usuário", required = true) SenhaInput senha);

        @Operation(summary = "Excluir usuário", responses = {
                        @ApiResponse(responseCode = "204", description = "Removido com sucesso"),
                        @ApiResponse(responseCode = "400", description = "ID usuário inválido", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
        })
        public void deletar(@Parameter(description = "Id usuário", example = "1", required = true) Long usuarioId);
}

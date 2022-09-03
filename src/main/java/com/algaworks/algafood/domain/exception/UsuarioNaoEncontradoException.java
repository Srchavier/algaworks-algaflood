package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de usuário com código %d";
	
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long usuarioId) {
		this(String.format(MSG_CIDADE_NAO_ENCONTRADA, usuarioId));
	}

}

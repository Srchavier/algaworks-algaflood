package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final String MSG_COZINHA_NAO_ENCONTADA = "Não existe um cadastro de cozinha com código %d";
	
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_COZINHA_NAO_ENCONTADA, estadoId));
	}

}

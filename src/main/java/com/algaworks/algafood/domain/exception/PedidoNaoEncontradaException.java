package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;


    public PedidoNaoEncontradaException(String codigo) {
        super(String.format("Não existe um pedido com código %s", codigo));
    }
    
}

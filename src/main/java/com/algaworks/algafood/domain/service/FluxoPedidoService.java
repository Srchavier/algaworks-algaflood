package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmitirPedidoService emitirPedidoService;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = emitirPedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
    }

    @Transactional
    public void entregue(String codigoPedido) {
        Pedido pedido = emitirPedidoService.buscarOuFalhar(codigoPedido);
        pedido.entregue();
    }

    @Transactional
    public void cancelado(String codigoPedido) {
        Pedido pedido = emitirPedidoService.buscarOuFalhar(codigoPedido);
        pedido.cancelado();
    }
}

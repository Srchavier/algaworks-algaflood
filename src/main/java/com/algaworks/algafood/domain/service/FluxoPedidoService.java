package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.publisher.PublicandoPedidoEvent;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmitirPedidoService emitirPedidoService;

    @Autowired
    private PublicandoPedidoEvent publisher;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = emitirPedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
        publisher.publish(new PedidoConfirmadoEvent(this, pedido));
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
        publisher.publish(new PedidoCanceladoEvent(this, pedido));
    }
}

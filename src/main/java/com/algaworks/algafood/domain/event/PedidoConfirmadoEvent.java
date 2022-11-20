package com.algaworks.algafood.domain.event;

import org.springframework.context.ApplicationEvent;

import com.algaworks.algafood.domain.model.Pedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoConfirmadoEvent extends ApplicationEvent {
    private Pedido pedido;

    public PedidoConfirmadoEvent(Object source, Pedido pedido) {
        super(source);
        this.pedido = pedido;
    }
 
}
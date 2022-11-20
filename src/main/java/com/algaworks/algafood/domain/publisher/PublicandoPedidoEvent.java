package com.algaworks.algafood.domain.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;

@Component
public class PublicandoPedidoEvent implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(PedidoConfirmadoEvent event) {
        this.publisher.publishEvent(event);
    }

    public void publish(PedidoCanceladoEvent event) {
        this.publisher.publishEvent(event);
    }
}

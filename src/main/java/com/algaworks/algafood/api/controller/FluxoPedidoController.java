package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.swaggerapi.controller.FluxoPedidoControllerSwagger;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("pedidos/{codigoPedido}")
public class FluxoPedidoController implements FluxoPedidoControllerSwagger {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/entregue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregue(@PathVariable String codigoPedido) {
        fluxoPedidoService.entregue(codigoPedido);
    }

    @PutMapping("/cancelado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelado(@PathVariable String codigoPedido) {
        fluxoPedidoService.cancelado(codigoPedido);
    }

}

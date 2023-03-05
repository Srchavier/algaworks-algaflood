package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.output.PedidoResumoModel;
import com.algaworks.algafood.api.v1.swaggerapi.ApiLinks;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks algaLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {

        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (Boolean.TRUE.equals(pedido.podeConfirmar())) {
            pedidoModel.add(algaLinks.linkToConfirmarPedido(pedido.getCodigo(), "confirmar"));
        }

        if (Boolean.TRUE.equals(pedido.podeEntregar())) {
            pedidoModel.add(algaLinks.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
        }

        if (Boolean.TRUE.equals(pedido.podeCancelar())) {
            pedidoModel.add(algaLinks.linkToCancelarPedido(pedido.getCodigo(), "cancelar"));
        }

        pedidoModel.getRestaurante().add(
                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoModel;
    }

}

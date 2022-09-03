package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoInputlDisassembler {

    @Autowired
    private ModelMapper modelMap;

    public Pedido toDomainObject(PedidoInput pedido) {
        return modelMap.map(pedido, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput input, Pedido pedido) {
        modelMap.map(input, pedido);
    }

}

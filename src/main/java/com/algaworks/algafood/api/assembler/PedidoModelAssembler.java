package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMap;

    public PedidoModel toModel(Pedido pedido) {
        return modelMap.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollection(List<Pedido> list) {
        return list.stream().map(this::toModel).collect(Collectors.toList());
    }

}

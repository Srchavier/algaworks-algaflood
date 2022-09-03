package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {

    @Autowired
    private ModelMapper modelMap;

    public PedidoResumoModel toModel(Pedido pedido) {
        return modelMap.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toCollection(List<Pedido> list) {
        return list.stream().map(this::toModel).collect(Collectors.toList());
    }

}

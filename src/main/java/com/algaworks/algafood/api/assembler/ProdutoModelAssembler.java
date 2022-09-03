package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModel toModel(Produto model) {
        return modelMapper.map(model, ProdutoModel.class);
    }

    public List<ProdutoModel> toCollectionModel(List<Produto> listModel) {
        return listModel.stream().map(this::toModel).collect(Collectors.toList());
    }
   
}

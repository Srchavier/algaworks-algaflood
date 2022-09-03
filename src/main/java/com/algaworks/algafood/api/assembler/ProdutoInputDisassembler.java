package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainMObject(ProdutoInput input) {
        return modelMapper.map(input, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput input, Produto output) {
        modelMapper.map(input, output);
    }
}
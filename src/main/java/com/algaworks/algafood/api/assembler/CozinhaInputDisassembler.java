package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainMObject(CozinhaInput input) {
        return modelMapper.map(input, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput input, Cozinha output) {
        modelMapper.map(input, output);
    }
}

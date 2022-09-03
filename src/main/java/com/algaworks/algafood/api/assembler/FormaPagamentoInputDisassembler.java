package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public FormaPagamento toDomainObject(FormaPagamentoInput input) {
        return modelMapper.map(input, FormaPagamento.class);
	}

    public void copyToDomainObject(FormaPagamentoInput restauranteInput, FormaPagamento restaurante) {
        modelMapper.map(restauranteInput, restaurante);
    }

}

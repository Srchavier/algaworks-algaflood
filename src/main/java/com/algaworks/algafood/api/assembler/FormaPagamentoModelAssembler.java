package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento output) {
		return modelMapper.map(output, FormaPagamentoModel.class);
	}

	public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> list) {
		return list.stream().map(this::toModel).collect(Collectors.toList());
	}

}

package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.RestauranteApenasNomeModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteApenasNomeModel.class);
	}

	public List<RestauranteApenasNomeModel> toCollectionModel(List<Restaurante> list) {
		return list.stream().map(this::toModel).collect(Collectors.toList());
	}
}
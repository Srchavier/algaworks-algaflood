package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.RestauranteBasicoModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public RestauranteBasicoModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteBasicoModel.class);
	}

	public List<RestauranteBasicoModel> toCollectionModel(List<Restaurante> list) {
		return list.stream().map(this::toModel).collect(Collectors.toList());
	}
}

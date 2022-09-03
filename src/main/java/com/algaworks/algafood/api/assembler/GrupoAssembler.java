package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoAssembler {

    @Autowired
    private ModelMapper mapper;

    public GrupoModel toModel(Grupo grupo) {
        return mapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> toCollectionModel(Collection<Grupo> list){
        return list.stream().map(this::toModel).collect(Collectors.toList());
    }
    
}

package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {
    
    @Autowired
    private ModelMapper mapper;

    public PermissaoModel toModel(Permissao output) {
       return mapper.map(output, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollection(Collection<Permissao> list) {
        return list.stream().map(this::toModel).collect(Collectors.toList());
    }

}

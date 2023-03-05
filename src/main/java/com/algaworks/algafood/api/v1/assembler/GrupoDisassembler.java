package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Grupo toDomainObject(GrupoInput input) {
        return mapper.map(input, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo){
        mapper.map(grupoInput, grupo);
    }
    
}

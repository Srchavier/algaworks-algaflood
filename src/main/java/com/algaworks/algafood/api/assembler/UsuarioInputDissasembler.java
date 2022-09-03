package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDissasembler {

    @Autowired
    private ModelMapper modelMapper;


    public Usuario toDomainObject(UsuarioInput input) {
        return this.modelMapper.map(input, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput input, Usuario usuario ) {
        this.modelMapper.map(input, usuario);
    }
    
}

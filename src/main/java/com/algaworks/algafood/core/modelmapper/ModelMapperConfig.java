package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.api.v1.model.output.EnderecoModel;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        var enderecoToEnderecoModelType = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        // enderecoToEnderecoModelType.<String>addMapping(src -> src.getCidade().getEstado().getNome() , (dest, value) -> dest.getCidade().setEstado(value));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class).addMappings(mapper -> mapper.skip(ItemPedido::setId));  

        return modelMapper;
    }
}

package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.output.GrupoModel;
import com.algaworks.algafood.api.v1.swaggerapi.ApiLinks;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel>{

    public GrupoAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
        grupoModel.add(apiLinks.linkToGrupos("grupos"));
        
        grupoModel.add(apiLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        
        return grupoModel;
    }
    
    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(apiLinks.linkToGrupos());
    }        
    
}

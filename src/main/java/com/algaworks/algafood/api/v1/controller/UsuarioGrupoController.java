package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.GrupoAssembler;
import com.algaworks.algafood.api.v1.model.output.GrupoModel;
import com.algaworks.algafood.api.v1.swaggerapi.ApiLinks;
import com.algaworks.algafood.api.v1.swaggerapi.controller.UsuarioGrupoControllerSwagger;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerSwagger {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoAssembler assembler;

    @Autowired
    private ApiLinks apiLinks;

    @GetMapping()
    public CollectionModel<GrupoModel> buscarTodosGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        CollectionModel<GrupoModel> gruposModel = assembler.toCollectionModel(usuario.getGrupos()).removeLinks()
                .add(apiLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

        gruposModel.getContent().forEach(grupoModel -> {
            grupoModel.add(apiLinks.linkToUsuarioGrupoDesassociacao(
                    usuarioId, grupoModel.getId(), "desassociar"));
        });

        return gruposModel;
    }

    @PutMapping(value = "/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarPermissao(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associarPermissao(usuarioId, grupoId);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping(value = "/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarPermissao(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarPermissao(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

}

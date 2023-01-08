package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.model.output.GrupoModel;
import com.algaworks.algafood.api.swaggerapi.controller.UsuarioGrupoControllerSwagger;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;


@RestController
@RequestMapping("usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerSwagger {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoAssembler assembler;


    @GetMapping()
    public List<GrupoModel> buscarTodosGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return assembler.toCollectionModel(usuario.getGrupos());
    }
    

    @PutMapping(value="/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associarPermissao(usuarioId, grupoId);
    }


    @DeleteMapping(value="/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarPermissao(usuarioId, grupoId);
    }
    
}

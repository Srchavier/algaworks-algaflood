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

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.output.PermissaoModel;
import com.algaworks.algafood.api.swaggerapi.controller.GrupoPermissaoControllerSwagger;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;



@RestController
@RequestMapping("grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerSwagger {

    @Autowired
    private PermissaoModelAssembler assembler;

    @Autowired
    private CadastroPermissaoService service;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @GetMapping()
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        return assembler.toCollection(grupo.getPermissoes());
    }

    @PutMapping(value="/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
         service.associarPermissao(grupoId, permissaoId);
    }


    @DeleteMapping(value="/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        service.desassociarPermissao(grupoId, permissaoId);
    }
    
}

package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.assembler.GrupoDisassembler;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.model.output.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoAssembler assembler;

    @Autowired
    private GrupoDisassembler disassembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return assembler.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel grupoPorId(@PathVariable Long grupoId) {
        return assembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoId));
    }

    @PostMapping
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput input) {
        Grupo grupo = disassembler.toDomainObject(input);
        return assembler.toModel(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput input) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        disassembler.copyToDomainObject(input, grupo);

        return assembler.toModel(cadastroGrupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId) {
        cadastroGrupoService.excluir(grupoId);
    }
    
}

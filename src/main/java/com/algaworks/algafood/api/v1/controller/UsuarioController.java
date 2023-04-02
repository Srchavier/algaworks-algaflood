package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDissasembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.output.UsuarioModel;
import com.algaworks.algafood.api.v1.swaggerapi.controller.UsuarioControllerSwagger;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerSwagger{

    @Autowired
    private UsuarioModelAssembler assemblerSemSenha;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioInputDissasembler dissasembler;

    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> usuarioEntity = usuarioRepository.findAll();
        return assemblerSemSenha.toCollectionModel(usuarioEntity);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
        Usuario buscarOuFalhar = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return assemblerSemSenha.toModel(buscarOuFalhar);
    }

    @PostMapping
    public UsuarioModel salvar(@Valid @RequestBody UsuarioComSenhaInput usuarioPrimeiroCadastro) {

        Usuario domainObject = dissasembler.toDomainObject(usuarioPrimeiroCadastro);
        Usuario usuarioNovo = cadastroUsuarioService.salvarNovoUsuario(domainObject, usuarioPrimeiroCadastro.getSenhaConfirmacao());

        return assemblerSemSenha.toModel(usuarioNovo);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel alterar(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioInput  usuarioInput) {

        Usuario buscarOuFalhar = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        dissasembler.copyToDomainObject(usuarioInput, buscarOuFalhar);
        Usuario salvar = cadastroUsuarioService.alterar(buscarOuFalhar);

        return assemblerSemSenha.toModel(salvar);
    }

    @PutMapping("/{usuarioId}/senha")
    public void alterar(@PathVariable Long usuarioId, @Valid @RequestBody SenhaInput senha) {
        cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
    
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId) {
        cadastroUsuarioService.excluir(usuarioId);
    }
}

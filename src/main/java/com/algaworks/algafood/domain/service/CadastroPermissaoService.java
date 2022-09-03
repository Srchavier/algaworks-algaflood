package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository repository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return repository.findById(permissaoId).orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

    /**
     * @param grupoId
     * @param permissaoId
     */
    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        Permissao permissao = this.buscarOuFalhar(permissaoId);
        grupo.associarPermissao(permissao);

    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        Permissao permissao = this.buscarOuFalhar(permissaoId);
        grupo.desassociarPermissao(permissao);
    }

}

package com.algaworks.algafood.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removida, pois está em uso";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService grupoService;

    /**
     * @param usuarioId
     * @return
     */
    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario alterar(Usuario usuario) { 
        usuarioRepository.detach(usuario);
        Optional<Usuario> findByEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if(findByEmail.isPresent() && !findByEmail.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usúario cadastrado com o e-mail %s", usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario salvarNovoUsuario(Usuario usuario, String senhaConfirmacao) {
        usuarioRepository.detach(usuario);
        Optional<Usuario> findByEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if(findByEmail.isPresent() && !findByEmail.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usúario cadastrado com o e-mail %s", usuario.getEmail()));
        }

        if(usuario.getSenha() != null && usuario.senhaNaoCoincideCom(senhaConfirmacao)) {
            throw new NegocioException("Senha informada não coincide com a senha do usuário.");
        }
        
        return usuarioRepository.save(usuario);
    }


    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_USUARIO_EM_USO, usuarioId));
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(novaSenha);
    }


    @Transactional
    public void associarPermissao(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarPermissao(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.desassociarGrupo(grupo);
    }


}

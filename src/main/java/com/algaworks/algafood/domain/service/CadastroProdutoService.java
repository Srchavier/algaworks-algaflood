package com.algaworks.algafood.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroProdutoService {
	private static final String MSG_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";

    private final ProdutoRepository repository;

    public Produto buscarOuFalhar(Long idRestaurante, Long idProduto) {
        return repository.findById(idRestaurante, idProduto).orElseThrow(() -> new ProdutoNaoEncontradoException(idRestaurante, idProduto));
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public void delete(Long idProduto) {
        try {
			repository.deleteById(idProduto);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(idProduto);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_EM_USO, idProduto));
		}
    }

    
    
}

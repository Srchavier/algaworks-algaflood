package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {

        Optional<FotoProduto> fotoExistente = produtoRepository
                .findFotoById(foto.getRestauranteId(), foto.getProduto().getId());

        String fotoAntiga = null;

        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
            fotoAntiga = fotoExistente.get().getNomeArquivo();
        }
        foto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo()));
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        var fotoNova = NovaFoto.builder()
                .nome(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .contentType(foto.getContentType())
                .build();

        fotoStorageService.substituir(fotoAntiga, fotoNova);

        return foto;
    }

    public void remover(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
        produtoRepository.delete(foto);
        produtoRepository.flush();
        fotoStorageService.remover(foto.getNomeArquivo());

    }

}

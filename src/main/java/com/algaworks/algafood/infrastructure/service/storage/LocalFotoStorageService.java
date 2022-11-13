package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

// @Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties localStorage;

    @Override
    public void armazenar(NovaFoto foto) {
        try {
            Path arquivoPath = getArquivoPath(foto.getNome());
    
            FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazer arquivo.", e);
        }
        
    }

    private Path getArquivoPath(String nomeArquivo) {
        return localStorage.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
        
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return FotoRecuperada.builder().inputStream(Files.newInputStream(arquivoPath)).build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível buscar arquivo.", e);
        }
    }

 
    
}

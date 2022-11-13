package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

public interface FotoStorageService {

    public void armazenar(NovaFoto foto);

    public void remover(String nomeArquivo);

    public FotoRecuperada recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    default void substituir(String fotoAntiga, NovaFoto fotoNova) {
        this.armazenar(fotoNova);
        if(fotoAntiga != null) {
            this.remover(fotoAntiga);
        }
    }

    @Data
    @Builder
    class NovaFoto {
        private String nome;
        private String contentType;
        private InputStream inputStream;
    }

    @Data
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputSttream() {
            return inputStream != null;
        }
    }

   

}

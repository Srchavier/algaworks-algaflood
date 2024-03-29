package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {
    void enviar(Mensagem mensagem);

    @Data
    @Builder
    class Mensagem {
        @Singular
        private Set<String> destinatarios;
        @NonNull
        private String assunto;
        @NonNull
        private String corpo;

        @Singular(value = "variavel")
        private Map<String, Object> variaveis;
    }
}

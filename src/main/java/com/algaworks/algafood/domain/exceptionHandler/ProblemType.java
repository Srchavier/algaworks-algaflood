package com.algaworks.algafood.domain.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    JSON_ERRADO("/mensagem-incompreensivel", "mensagem incompreensivel"),
    RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrado", "recurso-nao-encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "entidade-em-uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema");  

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
    
}

package com.algaworks.algafood.api.model.output;

import lombok.Data;

@Data
public class FotoProdutoModel {

    private Long id;
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
    
}

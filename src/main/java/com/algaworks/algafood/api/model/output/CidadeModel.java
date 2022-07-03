package com.algaworks.algafood.api.model.output;

import lombok.Data;

@Data
public class CidadeModel {

    private Long id;
    private String nome;
    private EstadoModel estado;
    
}

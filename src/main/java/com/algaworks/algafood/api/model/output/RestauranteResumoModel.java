package com.algaworks.algafood.api.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteResumoModel {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Thai Gourmet")
    private String nome;   
}  
package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CidadeIdInput {
    @NotNull
    private Long id;
}

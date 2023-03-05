package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnderecoInput {
    @Schema(example = "38400-000", required = true)
    @NotBlank
    private String cep;
    @Schema(example = "Rua e Lote 5 vila real arapoangas", required = true)
    @NotBlank
    private String logradouro;
    @Schema(example = "5", required = true)
    @NotBlank
    private String numero;
    @Schema(example = "casa em frente igreja", required = true)
    private String complemento;
    @Schema(example = "arapoangas", required = true)
    @NotBlank
    private String bairro;
    @Valid
    @NotNull
    private CidadeIdInput cidade;
}

package com.algaworks.algafood.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PedidoResumoModel {
    @Schema(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;
    @Schema(example = "298.90")
    private BigDecimal subtotal;
    @Schema(example = "10.00")
    private BigDecimal taxaFrete;
    @Schema(example = "308.90")
    private BigDecimal valorTotal;
    @Schema(example = "CRIADO")
    private String status;
    @Schema(example = "2022-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;
}

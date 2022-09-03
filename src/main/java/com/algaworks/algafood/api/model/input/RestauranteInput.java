package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Getter;
import lombok.Setter;

@ValorZeroIncluiDescricao(valorFiled = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Setter
@Getter
public class RestauranteInput {
    // @NotNull
	// @NotEmpty
	@NotBlank
    private String nome;
    
    @NotNull
	// @DecimalMin("0")
	// @PositiveOrZero
	@Multiplo(numero = 5)
	@TaxaFrete
    private BigDecimal taxaFrete;

	@Valid
	@NotNull
    private CozinhaIdInput cozinha;

	@Valid
	@NotNull
    private EnderecoInput endereco;
}

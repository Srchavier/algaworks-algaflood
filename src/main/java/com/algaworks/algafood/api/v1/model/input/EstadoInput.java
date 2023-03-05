package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInput {
	@Schema(description = "Id do estado", example = "1")
	private Long id;
	
	@Schema(description = "Nome do estado", example = "são paulo", required = true)
	@NotBlank
	private String nome;
}

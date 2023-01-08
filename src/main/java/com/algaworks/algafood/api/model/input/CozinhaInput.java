package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInput {
	@Schema(description = "ID da cozinha", example = "1")
	private Long id;

	@Schema(description = "Nome da cozinha", example = "cozinha 1")
	@NotBlank
	private String nome;
}

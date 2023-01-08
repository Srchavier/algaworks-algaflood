package com.algaworks.algafood.api.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FotoProdutoModel {

	@Schema(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
	private Long id;
	@Schema(example = "Prime Rib ao ponto")
	private String nomeArquivo;
	private String descricao;
	@Schema(example = "image/jpeg")
	private String contentType;
	@Schema(example = "202912")
	private Long tamanho;

}

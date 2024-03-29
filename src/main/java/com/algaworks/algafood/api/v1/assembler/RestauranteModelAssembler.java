package com.algaworks.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.output.RestauranteModel;
import com.algaworks.algafood.api.v1.swaggerapi.ApiLinks;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApiLinks algaLinks;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);

		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

		    
		if (restaurante.ativacaoPermitida()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
		}
	
		if (restaurante.inativacaoPermitida()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}
	
		if (restaurante.aberturaPermitida()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}
	
		if (restaurante.fechamentoPermitido()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}

		restauranteModel.add(algaLinks.linkToRestauranteProdutos(restaurante.getId(), "produtos"));

		restauranteModel.getCozinha().add(
				algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

		if (restauranteModel.getEndereco() != null) {
			restauranteModel.getEndereco().getCidade().add(
					algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

		}

		restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
				"formas-pagamento"));

		restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(),
				"responsaveis"));

		return restauranteModel;
	}

	public List<RestauranteModel> toColecionModel(List<Restaurante> list) {
		return list.stream().map(this::toModel).collect(Collectors.toList());
	}
}

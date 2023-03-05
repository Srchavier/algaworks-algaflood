package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.swaggerapi.ApiLinks;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel>{
    
	@Autowired
	private ApiLinks algaLinks;

    public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento output) {
		var model = createModelWithId(output.getId(), output);

		 modelMapper.map(output, model);

		 model.add(algaLinks.linkToFormasPagamento("formas-pagamento"));

		 return model;
	}

	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToFormasPagamento("formas-pagamento"));
	}

}

package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.output.ProdutoModel;
import com.algaworks.algafood.api.v1.swaggerapi.ApiLinks;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public ProdutoModel toModel(Produto produto) {
        ProdutoModel model = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

         modelMapper.map(produto, model);

         model.add(apiLinks.linkToRestauranteProdutos(model.getId(), "produtos"));

         model.add(apiLinks.linkToRestauranteProdutosFoto( produto.getRestaurante().getId(), model.getId(), "foto"));

         return model;
    }
   
}

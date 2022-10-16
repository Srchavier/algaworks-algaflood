package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.model.output.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value="restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private ProdutoModelAssembler assembler;

    @Autowired
    private ProdutoInputDisassembler  disassembler;

    @GetMapping
    public List<ProdutoModel> buscarTodosProdutos(@PathVariable Long idRestaurante, @RequestParam(required = false) boolean incluirInativos) {
        Restaurante buscarOuFalhar = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
        List<Produto> todosProdutos = null;
        if(incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(buscarOuFalhar);
        }else {
            todosProdutos = produtoRepository.findByAtivosByRestaurante(buscarOuFalhar);
        }
        return assembler.toCollectionModel(todosProdutos);
    }

    @GetMapping(value="/{idProduto}")
    public ProdutoModel buscarProdutoPorId(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

        Produto buscarOuFalhar = cadastroProdutoService.buscarOuFalhar(restaurante.getId(), idProduto);
        return assembler.toModel(buscarOuFalhar);
    }
    

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel salvaProduto(@PathVariable Long idRestaurante,@Valid  @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
        Produto produto = disassembler.toDomainMObject(produtoInput);
        produto.setRestaurante(restaurante);
        Produto salvar = cadastroProdutoService.salvar(produto);

        return assembler.toModel(salvar);
    }


    @PutMapping(value="/{idProduto}")
    public ProdutoModel alterarProduto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,@Valid  @RequestBody ProdutoInput produtoInput) {
        Produto produtoAtual  = cadastroProdutoService.buscarOuFalhar(idRestaurante, idProduto);
        disassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual  = cadastroProdutoService.salvar(produtoAtual);
        return assembler.toModel(produtoAtual);
    }

    @DeleteMapping(value="/{idProduto}")
    public void deletaProduto(@PathVariable Long idProduto) {
        cadastroProdutoService.delete(idProduto);
    }
    
    
}

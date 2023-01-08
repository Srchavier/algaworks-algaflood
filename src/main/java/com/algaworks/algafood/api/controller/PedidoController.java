package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoInputlDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.model.output.PedidoModel;
import com.algaworks.algafood.api.model.output.PedidoResumoModel;
import com.algaworks.algafood.api.swaggerapi.controller.PedidoControllerSwagger;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmitirPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("pedidos")
public class PedidoController implements PedidoControllerSwagger {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmitirPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoModelResumeAssembler;

    @Autowired
    private PedidoInputlDisassembler pedidoInputDisassembler;

    @GetMapping()
    public Page<PedidoResumoModel> todosPedidos(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPage(pageable);

        Page<Pedido> findAll = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

        List<PedidoResumoModel> collection = pedidoModelResumeAssembler.toCollection(findAll.getContent());

        return new PageImpl<>(collection, pageable, findAll.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel pedido(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(emissaoPedido.buscarOuFalhar(codigoPedido));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
    
            // TODO: pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(10L);
    
            novoPedido = emissaoPedido.emitir(novoPedido);
    
            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPage(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
            "codigo", "codigo",
            "restauranteNome", "restaurante.nome",
            "nomeCliente", "cliente.nome",
            "valorTotal", "valor.total"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}

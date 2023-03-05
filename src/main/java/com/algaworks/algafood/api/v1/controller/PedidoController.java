package com.algaworks.algafood.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PedidoInputlDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.model.output.PedidoModel;
import com.algaworks.algafood.api.v1.model.output.PedidoResumoModel;
import com.algaworks.algafood.api.v1.swaggerapi.controller.PedidoControllerSwagger;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmitirPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

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

    @Autowired
    private PagedResourcesAssembler<Pedido> assemblerPagedAssembler;

    @GetMapping()
    public PagedModel<PedidoResumoModel> todosPedidos(PedidoFilter filtro,
            @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPage(pageable);

        Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidoPage = new PageWrapper<>(pedidoPage, pageable);

        return assemblerPagedAssembler.toModel(pedidoPage, pedidoModelResumeAssembler);

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
        var mapeamento = Map.of(
            "codigo", "codigo",
            "subtotal", "subtotal",
            "taxaFrete", "taxaFrete",
            "valorTotal", "valorTotal",
            "dataCriacao", "dataCriacao",
            "restaurante.nome", "restaurante.nome",
            "restaurante.id", "restaurante.id",
            "cliente.id", "cliente.id",
            "cliente.nome", "cliente.nome"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}

package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.model.output.EstadoModel;
import com.algaworks.algafood.api.v1.swaggerapi.controller.EstadoControllerSwagger;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerSwagger {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler assembler;

	@Autowired
	private EstadoInputDisassembler disassembler;

	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		return assembler.toCollectionModel(estadoRepository.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return assembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@Valid @RequestBody EstadoInput estado) {
		return assembler.toModel(cadastroEstado.salvar(disassembler.toDomainObject(estado)));
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@Valid @PathVariable Long estadoId,
			@RequestBody EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		disassembler.copyToDomainObject(estadoInput, estadoAtual);
		return assembler.toModel(cadastroEstado.salvar(estadoAtual));

	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}

}

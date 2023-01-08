package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.output.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.output.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.output.RestauranteModel;
import com.algaworks.algafood.api.swaggerapi.controller.RestauranteControllerSwagger;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController implements RestauranteControllerSwagger {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteModelAssembler assembler;

	@Autowired
	private RestauranteApenasNomeModelAssembler apenasNomeModelAssembler;

	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	@Autowired
	private RestauranteInputDisassembler disassembler;


	// @JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	// @JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteApenasNomeModel> listarApenasNomes() {
		return apenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante buscarOuFalhar = cadastroRestaurante.buscarOuFalhar(restauranteId);
		return assembler.toModel(buscarOuFalhar);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@Valid @RequestBody RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = disassembler.toDomainObject(restauranteInput);
			return assembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
		disassembler.copyToDomainObject(restauranteInput, restauranteAtual);

		try {
			return assembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void aberto(@PathVariable Long restauranteId) {
		cadastroRestaurante.aberto(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void fechamento(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechamento(restauranteId);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void ativaMultiplas(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativa(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void inativaMultiplas(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativa(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void ativa(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativa(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void inativa(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativa(restauranteId);
	}

	@GetMapping("/por-taxa-frete")
	public List<RestauranteModel> restaurantesPorTaxaFrete(String nome, BigDecimal taxaInicial,
			BigDecimal taxaFinal) {
		return assembler.toColecionModel(restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}

	@GetMapping("/por-frete-gratis")
	public List<RestauranteModel> restaurantesPorFreteGratis(String nome) {
		return assembler.toColecionModel(restauranteRepository.findComFreteGratis(nome));
	}

	@GetMapping("/primeiro")
	public RestauranteModel restaurantesPrimeiro() {
		Optional<Restaurante> buscarPrimeiro = restauranteRepository.buscarPrimeiro();
		if (buscarPrimeiro.isEmpty()) {
			return new RestauranteModel();
		}
		return assembler.toModel(buscarPrimeiro.get());
	}

}

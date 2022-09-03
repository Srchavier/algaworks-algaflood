package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
	}

	@Transactional
	public void ativa(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.ativa();
	}

	@Transactional
	public void inativa(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.inativa();
	}

	@Transactional
	public void ativa(List<Long> restaurantesIds) {
		restaurantesIds.forEach(this::ativa);
	}

	@Transactional
	public void inativa(List<Long> restaurantesIds) {
		restaurantesIds.forEach(this::inativa);
	}

	@Transactional
	public void aberto(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.aberto();
	}

	@Transactional
	public void fechamento(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechado();
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		restaurante.removerFormaPagamento(formaPagamento);

	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		restaurante.adicionarFormaPagamento(formaPagamento);

	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
	
}

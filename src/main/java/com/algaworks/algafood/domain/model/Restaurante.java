package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@Embedded
	private Endereco endereco;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id", referencedColumnName = "id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
		joinColumns = @JoinColumn(name = "restaurante_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos;

	private Boolean ativo = Boolean.TRUE;

	private Boolean aberto = Boolean.FALSE;

	public void ativa() {
		setAtivo(true);
	}

	public void inativa() {
		setAtivo(false);
	}

	public void aberto() {
		setAberto(true);
	}

	public void fechado() {
		setAberto(false);
	}

	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public boolean isInativo() {
		return !isAtivo();
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public boolean aberturaPermitida() {
		return isAtivo() && isFechado();
	}
	
	public boolean ativacaoPermitida() {
		return isInativo();
	}
	
	public boolean inativacaoPermitida() {
		return isAtivo();
	}
	
	public boolean fechamentoPermitido() {
		return isAberto();
	}

	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}

	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean removerResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}
	
	public boolean adicionarResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}

	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().contains(formaPagamento);
	}
	
	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
		return !aceitaFormaPagamento(formaPagamento);
	}
}

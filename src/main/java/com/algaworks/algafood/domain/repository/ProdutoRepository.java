package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    @Query("from Produto where restaurante.id = :restaurante and id = :id")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("id") Long id);

    List<Produto> findTodosByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = ativo and p.restaurante = :restaurante")
    List<Produto> findByAtivosByRestaurante(Restaurante restaurante);

    @Query("select f from FotoProduto f join f.produto p where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);
    
}

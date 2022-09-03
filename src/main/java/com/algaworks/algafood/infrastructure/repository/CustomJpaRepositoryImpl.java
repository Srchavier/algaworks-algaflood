package com.algaworks.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager manager;
    
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();
        return Optional.ofNullable(manager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult());
    }

    @Override
    public void detach(T entity) {
        manager.detach(entity);
    }
    
}

package com.algaworks.algafood.api.v1.swaggerapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.output.PermissaoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Permissões")
public interface PermissaoControllerSwagger {
    
    @Operation(summary = "Lista as permissões")
	CollectionModel<PermissaoModel> listar();
}

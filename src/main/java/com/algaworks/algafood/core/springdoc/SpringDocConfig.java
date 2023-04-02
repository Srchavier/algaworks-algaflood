package com.algaworks.algafood.core.springdoc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.exceptionHandler.Problem;
import com.algaworks.algafood.api.v1.swaggerapi.model.LinksModelOpenApi;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
@SecurityScheme(name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class SpringDocConfig {

    private static final String NOT_FOUND_RESPONSE = "NotFoundResponse";
    private static final String NOT_ACCEPTTABLE_RESPONSE = "Recurso";
    private static final String INTERNAL_SERVE_ERROR_RESPONSE = "InternalService";
    private static final String BAD_REQUEST_RESPONSE = "BadRequestResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AlgaFood API")
                        .version("v1")
                        .description("Rest API do Algafood")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://google.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("AlgaWorks")
                        .url("https://algaworks.com"))
                .tags(Arrays.asList(
                        new Tag().name("Cidades").description("Gerencia as cidades"),
                        new Tag().name("Grupos").description("Gerencia os grupos"),
                        new Tag().name("Cozinhas").description("Gerencia as cozinhas"),
                        new Tag().name("Formas de pagamento").description("Gerencia as formas de pagamento"),
                        new Tag().name("Pedidos").description("Gerencia os pedidos"),
                        new Tag().name("Restaurantes").description("Gerencia os restaurantes"),
                        new Tag().name("Estados").description("Gerencia os estados"),
                        new Tag().name("Produtos").description("Gerencia os produtos"),
                        new Tag().name("Usuários").description("Gerencia os usuários"),
                        new Tag().name("Permissões").description("Gerencia as permissões"),
                        new Tag().name("Estatísticas").description("Estatísticas da AlgaFood"),
                        new Tag().name("Permissões").description("Gerencia as permissões")
                        ))
                .components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
                

    }

    /**
     * @return
     */
    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        // mudando objeto de links
        SpringDocUtils.getConfig()
        .replaceWithClass(Links.class, LinksModelOpenApi.class);

        return openApi -> {
            openApi.getPaths()
                    .values().stream()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();

                                switch (httpMethod) {
                                    case GET:
                                        responses.addApiResponse("404", new ApiResponse().$ref(NOT_FOUND_RESPONSE));
                                        responses.addApiResponse("500",
                                                new ApiResponse().$ref(INTERNAL_SERVE_ERROR_RESPONSE));
                                        responses.addApiResponse("406",
                                                new ApiResponse().$ref(NOT_ACCEPTTABLE_RESPONSE));
                                        break;
                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
                                        responses.addApiResponse("500",
                                                new ApiResponse().$ref(INTERNAL_SERVE_ERROR_RESPONSE));
                                        break;
                                    case PUT:
                                        responses.addApiResponse("404", new ApiResponse().$ref(NOT_FOUND_RESPONSE));
                                        responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
                                        responses.addApiResponse("500",
                                                new ApiResponse().$ref(INTERNAL_SERVE_ERROR_RESPONSE));
                                        break;
                                    case DELETE:
                                        responses.addApiResponse("404", new ApiResponse().$ref(NOT_FOUND_RESPONSE));
                                        responses.addApiResponse("500",
                                                new ApiResponse().$ref(INTERNAL_SERVE_ERROR_RESPONSE));
                                        break;
                                    default:
                                        break;
                                }

                            }));
        };
    }
    
    @SuppressWarnings("rawtypes")
    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();
        var problemSchema = ModelConverters.getInstance().read(Problem.class);
        var problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);
        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);
        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(BAD_REQUEST_RESPONSE, new ApiResponse().description("Requisição inválidar")
                .content(content));

        apiResponseMap.put(NOT_FOUND_RESPONSE, new ApiResponse().description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(INTERNAL_SERVE_ERROR_RESPONSE, new ApiResponse().description("Error interno no servidor")
                .content(content));

        apiResponseMap.put(NOT_ACCEPTTABLE_RESPONSE,
                new ApiResponse()
                        .description("Recurso não possui uma representação que poderia ser aceita pelo consumidor")
                        .content(content));

        return apiResponseMap;
    }

}
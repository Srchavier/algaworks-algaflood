package com.algaworks.algafood;

import static io.restassured.RestAssured.given;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

    @LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath ="/cozinhas";
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
			"/json/correto/cozinha-chinesa.json");
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	private void prepararDados() {
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);
	
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}

	// @Autowired
	// private CadastroCozinhaService cadastroCozinha;
	
	// @Test
	// public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
	// 	Cozinha novaCozinha = new Cozinha();
	// 	novaCozinha.setNome("Chinesa");
		
	// 	novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
	// 	assertThat(novaCozinha).isNotNull();
	// 	assertThat(novaCozinha.getId()).isNotNull();
	// }
	
	// @Test
	// public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
	// 	Cozinha novaCozinha = new Cozinha();
	// 	novaCozinha.setNome(null);
		
    //     ConstraintViolationException assertThrows2 = assertThrows( ConstraintViolationException.class, () -> {
    //         cadastroCozinha.salvar(novaCozinha);
    //     });

    //     assertNotNull(assertThrows2);
        
	// }
	
	// @Test()
	// public void deveFalhar_QuandoExcluirCozinhaEmUso() {

    //     EntidadeEmUsoException assertThrows2 = assertThrows( EntidadeEmUsoException.class, () -> {
    //         cadastroCozinha.excluir(1L);
    //     });

    //     assertNotNull(assertThrows2);
	// }

	// @Test()
	// public void deveFalhar_QuandoExcluirCozinhaInexistente() {

    //     CozinhaNaoEncontradaException assertThrows2 = assertThrows( CozinhaNaoEncontradaException.class, () -> {
    //         cadastroCozinha.excluir(100L);
    //     });

    //     assertNotNull(assertThrows2);
	// }

}

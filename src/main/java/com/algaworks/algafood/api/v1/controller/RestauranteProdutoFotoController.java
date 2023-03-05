package com.algaworks.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.model.output.FotoProdutoModel;
import com.algaworks.algafood.api.v1.swaggerapi.controller.RestauranteProdutoFotoControllerSwagger;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerSwagger{

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoModelAssembler assembler;

    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto foto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return assembler.toModel(foto);
    }

    @GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<InputStreamResource> servir(@PathVariable Long restauranteId,
            @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHearder)
            throws HttpMediaTypeNotAcceptableException {
        FotoProduto foto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(foto.getContentType());
        List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHearder);

        verificarCompatibiliidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

        FotoRecuperada fotoRecupera = fotoStorageService.recuperar(foto.getNomeArquivo());

        if (fotoRecupera.temUrl()) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecupera.getUrl())
                    .build();
        } else {
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecupera.getInputStream()));
        }
    }

    private void verificarCompatibiliidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas)
            throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalvar = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
        return assembler.toModel(fotoSalvar);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.remover(restauranteId, produtoId);

    }

}

package com.algaworks.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

// @Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties properties;

    @Override
    public void armazenar(NovaFoto foto) {

        try {
            String caminhoArquivo = getCaminhoArquivo(foto.getNome());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(foto.getContentType());
    
            var putObjectRequest = new PutObjectRequest(
                properties.getS3().getBucket(),
                caminhoArquivo,
                foto.getInputStream(),
                objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead);
    
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazer arquivo amazon S3.", e);
        }

    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
    
            var deleteObjectRequest = new DeleteObjectRequest(
                    properties.getS3().getBucket(),
                    caminhoArquivo);
    
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível deletar arquivo na amazon S3.", e);
        }

    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

        URL url = amazonS3.getUrl(properties.getS3().getBucket(), caminhoArquivo); 

        return FotoRecuperada.builder().url(url.toString()).build();
    }

    private String getCaminhoArquivo(String nome) {
        return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nome);
    }

}

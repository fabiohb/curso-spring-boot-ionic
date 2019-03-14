package com.fabiohb.cursos.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucket;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			return uploadFile(
				multipartFile.getInputStream(), 
				multipartFile.getOriginalFilename(), 
				multipartFile.getContentType()
			);
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO: ", e);
		}
	}
	
	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
		try {
			log.info("Iniciando upload");
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(contentType);
			s3client.putObject(bucket, fileName, inputStream, objectMetadata);
			log.info("Upload finalizado");
			return s3client.getUrl(bucket, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL em URI");
		}
	}
}

package com.fabiohb.cursos.cursomc.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucket;
	
	public void uploadFile(String localFilePath) {
		try {
			log.info("Iniciando upload");
			File file = new File(localFilePath);
			s3client.putObject(bucket, "teste.jpg", file);
			log.info("Upload finalizado");
		} catch (AmazonServiceException e) {
			log.error("AmazonServiceException: Status code = " + e.getErrorCode(), e);
		} catch (AmazonClientException e) {
			log.error("AmazonClientException: ", e);
		}
	}
}

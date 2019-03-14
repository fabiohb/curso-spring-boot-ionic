package com.fabiohb.cursos.cursomc.services;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fabiohb.cursos.cursomc.services.exceptions.FileException;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"png".equals(extension) && !"jpg".equals(extension)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}
		
		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			
			if ("png".equals(extension)) {
				img = pngToJpg(img);
			}
			
			return img;
		} catch (IOException e) {
			throw new FileException("Ero ao ler o arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImg = new BufferedImage(img.getWidth(), img.getHeight(), TYPE_INT_RGB);
		jpgImg.createGraphics().drawImage(jpgImg, 0, 0, Color.WHITE, null);
		return jpgImg;
	}
	
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Ero ao ler o arquivo");
		}
	}
}

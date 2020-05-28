package com.rhinodevs.crudbackend.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rhinodevs.crudbackend.services.exceptions.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"png".equals(extension) && !"jpg".equals(extension)) {
			throw new FileException("Somente imagens no formato jpg e png são permitidas");
		}
		try {
			BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(extension)) {
				image = pgnToJpg(image);
			}
			return image;
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo");
		}
	}

	public BufferedImage pgnToJpg(BufferedImage image) {
		BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	public InputStream getInputStream(BufferedImage image, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo");
		}
	}

	//Pega a imagem e retorna nova imagem recortada
	public BufferedImage cropSquare(BufferedImage sourceImage) {
		int min = (sourceImage.getHeight() <= sourceImage.getWidth() ? sourceImage.getHeight()
				: sourceImage.getWidth());
		return Scalr.crop(sourceImage, (sourceImage.getWidth() / 2) - (min / 2),
				(sourceImage.getHeight() / 2) - (min / 2), min, min);
	}
	
	//Redimensiona imagem
	public BufferedImage resize(BufferedImage sourceImage, int size) {
		return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);
	}

}

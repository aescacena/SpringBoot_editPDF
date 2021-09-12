package com.viafirma.pruebatecnica.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.viafirma.pruebatecnica.Utils.Utils;
import com.viafirma.pruebatecnica.entity.PDFInfo;

@Service
public class PDFStorageServiceImpl implements PDFStorageService{

	private final Path root = Paths.get("uploads");

	@Override
	public void init() {
		try {
			if (!Files.exists(root))
				Files.createDirectory(root);

		}catch (IOException e) {
			throw new RuntimeException("No puede inicializar la carpeta Uploads!");
		}		
	}

	@Override
	public PDFInfo save(MultipartFile file) throws Throwable {

		PDFInfo pdfInfo = null;


		if (!Files.exists(root))
			init();

		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getContentType());
		
		if (!(file.getOriginalFilename().contains("jpg")) && !(file.getOriginalFilename().contains("png")))
			return null;
		
		pdfInfo = new PDFInfo(Utils.getFileName(file.getOriginalFilename()), root.toString());
		String realPath = pdfInfo.getUrl() + "/" + pdfInfo.getIdDocument() + ".pdf";

		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Image
		PDImageXObject image = PDImageXObject.createFromByteArray(document, file.getBytes(), pdfInfo.getIdDocument());
		//contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);
		contentStream.drawImage(image, 0, 0, image.getWidth(), image.getHeight());

		contentStream.close();

		document.save(realPath);

		return pdfInfo;


	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("No puede leer el fichero!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		}catch (IOException e) {
			throw new RuntimeException("No puede leer los ficheros!");
		}
	}

}

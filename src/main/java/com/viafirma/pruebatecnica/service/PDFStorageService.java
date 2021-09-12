package com.viafirma.pruebatecnica.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.viafirma.pruebatecnica.entity.PDFInfo;

public interface PDFStorageService {
	
	/**
	 * 
	 */
	public void init();
	
	/**
	 * Almacena fichero en disco
	 * @param file
	 * @throws Throwable 
	 */
	public PDFInfo save(MultipartFile file) throws Throwable;
	
	/**
	 * Lee ficnero de disco
	 * @param filename
	 * @return
	 */
	public Resource load (String filename);

	/**
	 * Elimina todos los ficheros de disco
	 */
	public void deleteAll();
	
	/**
	 * Lee todos los ficheros de disco
	 * @return
	 */
	public Stream<Path> loadAll();
}

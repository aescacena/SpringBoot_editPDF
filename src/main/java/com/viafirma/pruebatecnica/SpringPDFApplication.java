package com.viafirma.pruebatecnica;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.viafirma.pruebatecnica.service.PDFStorageService;

@SpringBootApplication
public class SpringPDFApplication implements CommandLineRunner {

	@Resource
	PDFStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringPDFApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
	    storageService.init();
	}

}

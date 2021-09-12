package com.viafirma.pruebatecnica.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viafirma.pruebatecnica.entity.PDFInfo;
import com.viafirma.pruebatecnica.message.ResponseMessage;
import com.viafirma.pruebatecnica.service.PDFStorageService;

@Controller
@RequestMapping("/")
public class PDFController {
	
	@Autowired
	PDFStorageService storageService;
	
	@PostMapping("img2pdf")
	public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file){
		
		String message = "";
		PDFInfo pdfInfo = null;
        ObjectMapper Obj = new ObjectMapper();
		
		try {
			pdfInfo = storageService.save(file);
			message = Obj.writeValueAsString(pdfInfo);

            return ResponseEntity.status(HttpStatus.OK).body(message);
		}catch (Exception e) {
			message = "No se ha podido crear el fichero con nombre: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	@GetMapping("files")
	public ResponseEntity<List<PDFInfo>> getListFiles(){
		List<PDFInfo> fileInfos = storageService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(PDFController.class, "getFile", path.getFileName().toString()).build().toString();

		      return new PDFInfo(filename, url);
		    }).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}
	
	@GetMapping("files/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
	    Resource file = storageService.load(filename);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
	
	@GetMapping("pdf")
	public ResponseEntity<Resource> downloadFile(@RequestParam String filename){
		
		HttpHeaders header = new HttpHeaders();
		ByteArrayResource resource = null;
		
		File file = null;
		try {
			file = storageService.load(filename).getFile();
	        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename+".pdf");
	        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        header.add("Pragma", "no-cache");
	        header.add("Expires", "0");
	
	        Path path = Paths.get(file.getAbsolutePath());
			resource = new ByteArrayResource(Files.readAllBytes(path));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
	
	@GetMapping("delete")
	public ResponseEntity<ResponseMessage> deleteFiles(){
		String message = "";
		storageService.deleteAll();
		
		message = "Se han eliminado todos los ficheros";
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	}
	
}

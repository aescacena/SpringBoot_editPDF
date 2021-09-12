package com.viafirma.pruebatecnica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

	@GetMapping("/dddd")
	public String inicio() {
		return "redirect:/";
	}

}

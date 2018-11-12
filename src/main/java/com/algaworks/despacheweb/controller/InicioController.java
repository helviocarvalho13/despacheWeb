package com.algaworks.despacheweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
/*Anotação para dizer que a classe é um controller, 
 * assim, podendo usar o @ComponentScan na classe WebConfig*/

@RequestMapping("/")
public class InicioController {
	
	@GetMapping("")
	public ModelAndView inicio(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("inicio/Inicio");
		return mv;
	}
	
	

	
	
}

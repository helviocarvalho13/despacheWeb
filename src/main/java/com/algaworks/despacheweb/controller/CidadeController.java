package com.algaworks.despacheweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.despacheweb.model.Cidade;
import com.algaworks.despacheweb.service.CidadeService;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeService cidadeService;
	
	@Cacheable("cidades")
	@RequestMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> retornaCidadesPorEstado(@RequestParam(name = "estado", defaultValue="-1") Long codigoEstado){
		try{
			Thread.sleep(500);
		}catch(InterruptedException e){
			
		}
		return cidadeService.retornaCidadesPorEstado(codigoEstado);
	}

}

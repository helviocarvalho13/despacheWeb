package com.algaworks.despacheweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.despacheweb.controller.page.PageWrapper;
import com.algaworks.despacheweb.model.Veiculo;
import com.algaworks.despacheweb.repo.filter.VeiculoFilter;
import com.algaworks.despacheweb.service.EstadoService;
import com.algaworks.despacheweb.service.VeiculoService;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private EstadoService estadoService;
	
	private ModelAndView buildListAll(String viewName){
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("estados", estadoService.retornaTodosEstados());
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Veiculo veiculo){
		ModelAndView mv = buildListAll("veiculo/CadastroVeiculo");
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(VeiculoFilter veiculoFilter, BindingResult bindingResult, @PageableDefault(size=3) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView();
		mv = buildListAll("veiculo/PesquisaVeiculos");
		//PageWrapper<Veiculo> pagina = new PageWrapper<>(veiculoService.filtrar(veiculoFilter, pageable), httpServletRequest);
		//mv.addObject("pagina", pagina);
		return mv;
	}

}

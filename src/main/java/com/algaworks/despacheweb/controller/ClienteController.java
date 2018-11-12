package com.algaworks.despacheweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.despacheweb.controller.page.PageWrapper;
import com.algaworks.despacheweb.enums.TipoPessoa;
import com.algaworks.despacheweb.model.Cliente;
import com.algaworks.despacheweb.repo.filter.ClienteFilter;
import com.algaworks.despacheweb.service.ClienteService;
import com.algaworks.despacheweb.service.EstadoService;
import com.algaworks.despacheweb.service.exception.CpfCnpjClienteJaCadastradoException;
import com.algaworks.despacheweb.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EstadoService estadoService;
	
	private ModelAndView buildListAll(String viewName){
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("tipoPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoService.retornaTodosEstados());
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente){
		ModelAndView mv = buildListAll("cliente/CadastroCliente");
		return mv;
	}
	
	@PostMapping(value= {"/novo", "{\\d+}"})
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes){
		if(result.hasErrors()){
			return novo(cliente);
		}
		try{
			clienteService.salvar(cliente);
		}catch(CpfCnpjClienteJaCadastradoException e){
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		redirectAttributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso.");
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult bindingResult, @PageableDefault(size=3) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView();
		mv = buildListAll("cliente/PesquisaClientes");
		PageWrapper<Cliente> pagina = new PageWrapper<>(clienteService.filtrar(clienteFilter, pageable), httpServletRequest);
		mv.addObject("pagina", pagina);
		return mv;
	}
	
	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		//return clienteService.retornaClientesPeloNome(nome);
		return clienteService.findByNomeStartingWithIgnoreCase(nome);
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente){
		try{
			clienteService.excluir(cliente);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cliente cliente){
		cliente = clienteService.retornaCliente(cliente);
		ModelAndView mv = novo(cliente);
		mv.addObject(cliente);
		return mv;
		
	}

	private void validarTamanhoNome(String nome) {
		if(StringUtils.isEmpty(nome) || nome.length() < 3){
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e){
		return ResponseEntity.badRequest().build();
	}

}

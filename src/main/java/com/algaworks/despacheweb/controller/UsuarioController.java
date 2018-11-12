	package com.algaworks.despacheweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.despacheweb.controller.page.PageWrapper;
import com.algaworks.despacheweb.enums.StatusUsuario;
import com.algaworks.despacheweb.model.Usuario;
import com.algaworks.despacheweb.repo.filter.UsuarioFilter;
import com.algaworks.despacheweb.service.GrupoService;
import com.algaworks.despacheweb.service.UsuarioService;
import com.algaworks.despacheweb.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.despacheweb.service.exception.UsuarioJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoService grupoService;
	
	private ModelAndView buildListAll(String viewName){
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("grupos", grupoService.retornaTodosGrupos());
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv = buildListAll("usuario/CadastroUsuario");
		return mv;
	}
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + usuario.getAtivo());
		
		if(result.hasErrors()){
			//usuario.getDataNascimento();
			return novo(usuario);
		}
		try{
			usuarioService.salvar(usuario);
		}catch(UsuarioJaCadastradoException e){
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		redirectAttributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso.");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult bindingResult, @PageableDefault(size=4) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView();
		mv = buildListAll("usuario/PesquisaUsuarios");
		PageWrapper<Usuario> pagina = new PageWrapper<>(usuarioService.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", pagina);
		return mv;
	}
	
	@PutMapping("/status")
	//@RequestMapping(value = "/status", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario status){
		usuarioService.alterarStatus(codigos, status);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		Usuario usuario = usuarioService.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Usuario usuario){
		try{
			usuarioService.excluir(usuario);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}

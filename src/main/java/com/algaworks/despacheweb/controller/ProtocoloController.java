package com.algaworks.despacheweb.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.despacheweb.controller.page.PageWrapper;
import com.algaworks.despacheweb.model.Protocolo;
import com.algaworks.despacheweb.repo.filter.ProtocoloFilter;
import com.algaworks.despacheweb.security.UsuarioSistema;
import com.algaworks.despacheweb.service.ClienteService;
import com.algaworks.despacheweb.service.ProtocoloService;

@Controller
@RequestMapping("/protocolos")
public class ProtocoloController {

	@Autowired
	private ProtocoloService protocoloService;
	
	@Autowired
	private ClienteService clienteService;
	
	private ModelAndView buildListAll(String viewName){
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("clientes", clienteService.retornaTodosClientes());
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Protocolo protocolo){
		ModelAndView mv = buildListAll("protocolo/CadastroProtocolo");
		return mv;
	}
	
	@PostMapping(value={"/novo", "{\\d+}"})
	public ModelAndView cadastrar(@Valid Protocolo protocolo, BindingResult result, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		if(result.hasErrors()){
			return novo(protocolo);
		}
		
		try{
			protocoloService.salvar(protocolo, usuarioSistema);
		}catch(Exception e){
			result.rejectValue("assinatura", e.getMessage(), e.getMessage());
			return novo(protocolo);
		}
		
		redirectAttributes.addFlashAttribute("mensagem", "Protocolo salvo com sucesso.");
		return new ModelAndView("redirect:/protocolos/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ProtocoloFilter protocoloFilter, BindingResult bindingResult, @PageableDefault(size=10) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView();
		mv = buildListAll("protocolo/PesquisaProtocolos");
		PageWrapper<Protocolo> pagina = new PageWrapper<>(protocoloService.filtrar(protocoloFilter, pageable), httpServletRequest);
		mv.addObject("pagina", pagina);
		return mv;
	}
	
	@GetMapping("/download/{codigo}")
    public HttpEntity<byte[]> download(@PathVariable("codigo") Protocolo protocolo) throws IOException {
    	
    	protocolo = protocoloService.retornaProtocolo(protocolo);
    	
        //byte[] arquivo = Files.readAllBytes( Paths.get("/home/wolmir/minha-imagem.jpg") );

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"assinatura.pdf\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( protocolo.getAssinatura(), httpHeaders);

        return entity;
    }
    
    @PostMapping(value = "/cancelar") 
	public ModelAndView cancelar(Protocolo protocolo, BindingResult result, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
    	if(result.hasErrors()){
			return novo(protocolo);
		}
    	
    	try{
    		protocoloService.cancelar(protocolo, usuarioSistema);
		}catch(Exception e){
			//result.rejectValue("assinatura", e.getMessage(), e.getMessage());
			//return novo(protocolo);
			throw new RuntimeException("Erro ao cancelar Protocolo! Por favor, tente novamente ou contacte o suporte!");
		}
				
		
		redirectAttributes.addFlashAttribute("mensagem", "Protocolo cancelado com sucesso.");
		return new ModelAndView("redirect:/protocolos/novo");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Protocolo protocolo){
		protocolo = protocoloService.retornaProtocolo(protocolo);
		ModelAndView mv = novo(protocolo);
		mv.addObject(protocolo);
		//mv.addObject("imagem", converterBytesToImage(protocolo.getAssinatura()));
		return mv;
	}

	/*private Image converterBytesToImage(byte[] bytes) {
		Image imagem = new ImageIcon(bytes).getImage();
		return imagem;
	}*/

}

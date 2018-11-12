package com.algaworks.despacheweb.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	
	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest){
		this.page = page;
		//this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
		String httpUrl = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString().replaceAll("\\+", "%20").replaceAll("excluido", "");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);		
	}
	
	public List<T> getConteudo(){
		return page.getContent();
	}
	
	public Boolean isVazia(){
		return page.getContent().isEmpty();
	}
	
	public Integer getAtual(){
		return page.getNumber();
	}
	
	public Boolean isPrimeira(){
		return page.isFirst();
	}
	
	public Boolean isUltima(){
		return page.isLast();
	}
	
	public Integer getTotal(){
		return page.getTotalPages();
	}
	
	public String urlParaPagina(Integer pagina){
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toString();
	}

	public String urlOrdenada(String propriedade){
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uriBuilder.build(true).encode().toString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		
		return uriComponentsBuilder.replaceQueryParam("sort", valorSort).build(true).encode().toString();
		
		
	}

	private String inverterDirecao(String propriedade) {
		String direcao = "asc";
		
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		if(order != null){
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}
		
		return direcao;
	}
	
	public Boolean descendente(String propriedade){
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public Boolean ordenada(String propriedade){
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		
		if(order == null){
			return false;
		}
		
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}

}

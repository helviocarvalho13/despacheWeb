package com.algaworks.despacheweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.despacheweb.model.Estado;
import com.algaworks.despacheweb.repo.EstadoRepo;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepo estadoRepo;

	public List<Estado> retornaTodosEstados() {
		return estadoRepo.retornaTodosEstados();
	}
	
	

}

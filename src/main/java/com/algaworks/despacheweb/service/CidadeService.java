package com.algaworks.despacheweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.despacheweb.model.Cidade;
import com.algaworks.despacheweb.repo.CidadeRepo;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepo cidadeRepo;

	public List<Cidade> retornaCidadesPorEstado(Long codigoEstado) {
		return cidadeRepo.findByEstadoCodigo(codigoEstado);
	}

}

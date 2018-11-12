package com.algaworks.despacheweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.despacheweb.model.Grupo;
import com.algaworks.despacheweb.repo.GrupoRepo;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepo grupoRepo;

	public List<Grupo> retornaTodosGrupos() {
		return grupoRepo.findAll();
	}
}

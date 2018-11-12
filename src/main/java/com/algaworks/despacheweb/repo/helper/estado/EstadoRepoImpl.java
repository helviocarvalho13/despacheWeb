package com.algaworks.despacheweb.repo.helper.estado;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.algaworks.despacheweb.model.Estado;

public class EstadoRepoImpl implements EstadoQueries{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	@Transactional(readOnly=true)
	public List<Estado> retornaTodosEstados() {
		String jpql = "FROM Estado e ORDER BY e.nome ASC";
		List<Estado> retorno = manager.createQuery(jpql, Estado.class).getResultList();
		return retorno;
	}

}

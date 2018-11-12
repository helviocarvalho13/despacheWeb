package com.algaworks.despacheweb.repo.helper.veiculo;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.despacheweb.model.Veiculo;
import com.algaworks.despacheweb.repo.filter.VeiculoFilter;
import com.algaworks.despacheweb.repo.paginacao.PaginacaoUtil;

public class VeiculoRepoImpl implements VeiculoQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Veiculo> filtrar(VeiculoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Veiculo.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionaFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private void adicionaFiltro(VeiculoFilter filtro, Criteria criteria) {
		
	}
	
	private Long total(VeiculoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Veiculo.class);
		adicionaFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Veiculo retornaVeiculo(Veiculo veiculo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Veiculo.class);
		criteria.add(Restrictions.eq("codigo", veiculo.getCodigo()));
		return (Veiculo) criteria.uniqueResult();
	}

	@Override
	public Optional<Veiculo> findByPlaca(String placa) {
		// TODO Auto-generated method stub
		return null;
	}

}

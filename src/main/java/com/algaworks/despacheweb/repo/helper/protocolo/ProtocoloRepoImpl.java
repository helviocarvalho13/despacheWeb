package com.algaworks.despacheweb.repo.helper.protocolo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.despacheweb.model.Protocolo;
import com.algaworks.despacheweb.repo.filter.ProtocoloFilter;
import com.algaworks.despacheweb.repo.paginacao.PaginacaoUtil;

public class ProtocoloRepoImpl implements ProtocoloQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Protocolo> filtrar(ProtocoloFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Protocolo.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionaFiltro(filtro, criteria);
		criteria.createAlias("cliente", "c", JoinType.LEFT_OUTER_JOIN);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(ProtocoloFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Protocolo.class);
		adicionaFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionaFiltro(ProtocoloFilter filtro, Criteria criteria) {
		if(filtro != null){
			if (!StringUtils.isEmpty(filtro.getDescricao())) {
				criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
			}
			
			if(filtro.getDesde() != null){
				criteria.add(Restrictions.ge("data", filtro.getDesde()));
			}
			
			if(filtro.getAte() != null){
				criteria.add(Restrictions.le("data", filtro.getAte()));
			}
			
			if (isClientePresente(filtro)) {
				criteria.add(Restrictions.eq("c", filtro.getCliente()));
			}
		}
	}
	
	
	private boolean isClientePresente(ProtocoloFilter filtro) {
		return filtro.getCliente() != null && filtro.getCliente().getCodigo() != null;
	}

	@Override
	@Transactional(readOnly=true)
	public Protocolo retornaProtocolo(Protocolo protocolo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Protocolo.class);
		criteria.createAlias("cliente","c", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", protocolo.getCodigo()));
		return (Protocolo) criteria.uniqueResult();
	}

}

package com.algaworks.despacheweb.repo.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.despacheweb.model.Grupo;
import com.algaworks.despacheweb.model.Usuario;
import com.algaworks.despacheweb.model.UsuarioGrupo;
import com.algaworks.despacheweb.repo.filter.UsuarioFilter;
import com.algaworks.despacheweb.repo.paginacao.PaginacaoUtil;

public class UsuarioRepoImpl implements UsuarioQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Usuario> retornaUsuarioAtivoPorEmail(String email) {
		String jpql = "FROM Usuario u WHERE LOWER(u.email) = LOWER(:email) and u.ativo = true";
		Optional<Usuario> retorno = manager.createQuery(jpql, Usuario.class)
									.setParameter("email", email)
										.getResultList().stream().findFirst();
		return retorno;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Usuario buscarComGrupos(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}

	@Override
	public List<String> retornaPermissoes(Usuario usuario) {
		String jpql = "SELECT DISTINCT p.nome FROM Usuario u INNER JOIN u.grupos g"
				+ " INNER JOIN g.permissoes p WHERE u = :usuario";
		List<String> retorno = manager.createQuery(jpql, String.class)
								.setParameter("usuario", usuario)
									.getResultList();
		
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		paginacaoUtil.preparar(criteria, pageable);
		adicionaFiltro(filtro, criteria);
		
		List<Usuario> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}
	
	private void adicionaFiltro(UsuarioFilter filtro, Criteria criteria) {
		if(filtro != null){
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.EXACT));
			}
			
			//criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);	
			
			if(filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()){
				List<Criterion> subQueries = new ArrayList<>();
				for(Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()){
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));
					
					subQueries.add(Subqueries.propertyIn("codigo", dc));
				}
				
				Criterion[] criterions = new Criterion[subQueries.size()];
				criteria.add(Restrictions.and(subQueries.toArray(criterions)));
			}
		}
	}
	
	private Long total(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionaFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	

}

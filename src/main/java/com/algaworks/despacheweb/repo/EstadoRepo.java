package com.algaworks.despacheweb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.Estado;
import com.algaworks.despacheweb.repo.helper.estado.EstadoQueries;

@Repository
public interface EstadoRepo extends JpaRepository<Estado, Long>, EstadoQueries{

	


}

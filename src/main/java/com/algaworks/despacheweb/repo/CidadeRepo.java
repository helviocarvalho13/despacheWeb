package com.algaworks.despacheweb.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.Cidade;

@Repository
public interface CidadeRepo extends JpaRepository<Cidade, Long>{

	List<Cidade> findByEstadoCodigo(Long codigoEstado);


}

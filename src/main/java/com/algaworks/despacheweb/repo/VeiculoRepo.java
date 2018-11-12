package com.algaworks.despacheweb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.Veiculo;
import com.algaworks.despacheweb.repo.helper.veiculo.VeiculoQueries;

@Repository
public interface VeiculoRepo extends JpaRepository<Veiculo, Long>, VeiculoQueries{

}

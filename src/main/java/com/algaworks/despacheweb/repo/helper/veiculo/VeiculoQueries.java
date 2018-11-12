package com.algaworks.despacheweb.repo.helper.veiculo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.despacheweb.model.Veiculo;
import com.algaworks.despacheweb.repo.filter.VeiculoFilter;

public interface VeiculoQueries {
	
	Veiculo retornaVeiculo(Veiculo veiculo);
	
	Optional<Veiculo> findByPlaca(String placa);
	
	Page<Veiculo> filtrar(VeiculoFilter veiculoFilter, Pageable pageable);

}

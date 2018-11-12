package com.algaworks.despacheweb.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.despacheweb.model.Veiculo;
import com.algaworks.despacheweb.repo.VeiculoRepo;
import com.algaworks.despacheweb.repo.filter.VeiculoFilter;
import com.algaworks.despacheweb.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.despacheweb.service.exception.PlacaVeiculoJaCadastradoException;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepo veiculoRepo;
	
	@Transactional(readOnly=true)
	public List<Veiculo> retornaTodosVeiculos() {
		List<Veiculo> lista = veiculoRepo.findAll();
		return lista;
	}

	public Page<Veiculo> filtrar(VeiculoFilter veiculoFilter, Pageable pageable) {
		return veiculoRepo.filtrar(veiculoFilter, pageable);
	}

	@Transactional
	public void salvar(@Valid Veiculo veiculo) {
		
		Optional<Veiculo> veiculoExistente = veiculoRepo.findByPlaca(veiculo.getPlaca());
		if(veiculo.getCodigo() != null) {
			if(veiculoExistente.isPresent() && !(veiculo.getCodigo().compareTo(veiculoExistente.get().getCodigo()) == 0)){
				throw new PlacaVeiculoJaCadastradoException("Placa de Veículo já cadastrado.");
			}
		}else {
			if(veiculoExistente.isPresent()) {
				throw new PlacaVeiculoJaCadastradoException("Placa de Veículo já cadastrado.");
			}
		}
		
		veiculoRepo.save(veiculo);
		
	}

	public void excluir(Veiculo veiculo) {
		try{
			veiculoRepo.delete(veiculo);
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar veículo. Veículo já vinculado a alguma informação no sistema!");
		}
	}

	@Transactional(readOnly=true)
	public Veiculo retornaVeiculo(Veiculo veiculo) {
		return veiculoRepo.retornaVeiculo(veiculo);
	}

	public Integer totalVeiculos() {
		return veiculoRepo.findAll().size();
	}

}

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

import com.algaworks.despacheweb.model.Cliente;
import com.algaworks.despacheweb.repo.ClienteRepo;
import com.algaworks.despacheweb.repo.filter.ClienteFilter;
import com.algaworks.despacheweb.service.exception.CpfCnpjClienteJaCadastradoException;
import com.algaworks.despacheweb.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepo clienteRepo;
	
	@Transactional(readOnly=true)
	public List<Cliente> retornaTodosClientes() {
		List<Cliente> lista = clienteRepo.findAll();
		return lista;
	}

	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable) {
		return clienteRepo.filtrar(clienteFilter, pageable);
	}

	@Transactional
	public void salvar(@Valid Cliente cliente) {
		
		Optional<Cliente> clienteExistente = clienteRepo.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		if(cliente.getCodigo() != null) {
			if(clienteExistente.isPresent() && !(cliente.getCodigo().compareTo(clienteExistente.get().getCodigo()) == 0)){
				throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado.");
			}
		}else {
			if(clienteExistente.isPresent()) {
				throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado.");
			}
		}
		
		clienteRepo.save(cliente);
		
	}

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome) {
		return clienteRepo.findByNomeStartingWithIgnoreCase(nome);
	}

	public void excluir(Cliente cliente) {
		try{
			clienteRepo.delete(cliente);
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cliente. Cliente já vinculado a alguma informação no sistema!");
		}
	}

	@Transactional(readOnly=true)
	public Cliente retornaCliente(Cliente cliente) {
		return clienteRepo.retornaCliente(cliente);
	}

	public Integer totalClientes() {
		return clienteRepo.findAll().size();
	}
	
}

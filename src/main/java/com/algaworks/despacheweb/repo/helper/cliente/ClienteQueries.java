package com.algaworks.despacheweb.repo.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.despacheweb.model.Cliente;
import com.algaworks.despacheweb.repo.filter.ClienteFilter;

public interface ClienteQueries {
	
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);
	
	Cliente retornaCliente(Cliente cliente);


}

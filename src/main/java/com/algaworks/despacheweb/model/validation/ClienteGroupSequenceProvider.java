package com.algaworks.despacheweb.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.algaworks.despacheweb.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente>{

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> grupos = new ArrayList<>();
		grupos.add(Cliente.class);
		
		if(cliente != null && cliente.getTipoPessoa() != null){
			grupos.add(cliente.getTipoPessoa().getGrupo());
		}
		
		return grupos;
	}
}

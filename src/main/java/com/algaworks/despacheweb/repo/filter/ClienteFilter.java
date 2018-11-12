package com.algaworks.despacheweb.repo.filter;

import com.algaworks.despacheweb.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteFilter {

	private String nome;
	private String cpfOuCnpj;
	private Estado estado;
}

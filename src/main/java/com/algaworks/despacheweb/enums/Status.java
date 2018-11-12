package com.algaworks.despacheweb.enums;

import lombok.Getter;

@Getter
public enum Status {

	CRIADO("Criado"),
	EDITADO("Editado"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	private Status(String descricao){
		this.descricao = descricao;
	}
}

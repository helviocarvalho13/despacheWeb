package com.algaworks.despacheweb.enums;

import lombok.Getter;

@Getter
public enum Genero {
	
	MASCULINO("Masculino"),
	FEMININO("Feminino");
	
	private String descricao;
	
	private Genero(String descricao){
		this.descricao = descricao;
	}

}

package com.algaworks.despacheweb.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.algaworks.despacheweb.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade>{

	@Override
	public Cidade convert(String codigo) {
		if(!codigo.isEmpty()){
			Cidade cidade = new Cidade();
			cidade.setCodigo(new Long(codigo));
			return cidade;
		}
		return null;
	}

}

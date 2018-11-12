package com.algaworks.despacheweb.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.algaworks.despacheweb.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo>{
	
	@Override
	public Grupo convert(String codigo) {
		if(!codigo.isEmpty()){
			Grupo grupo = new Grupo();
			grupo.setCodigo(new Long(codigo));
			return grupo;
		}
		return null;
	}

}

package com.algaworks.despacheweb.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.algaworks.despacheweb.model.Estado;

public class EstadoConverter implements Converter<String, Estado>{

	@Override
	public Estado convert(String codigo) {
		if(!codigo.isEmpty()){
			Estado estado = new Estado();
			estado.setCodigo(new Long(codigo));
			return estado;
		}
		return null;
	}

}

package com.algaworks.despacheweb.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USUARIO_GRUPO")
@Getter
@Setter
@EqualsAndHashCode
public class UsuarioGrupo {
	
	@EmbeddedId
	private UsuarioGrupoId id;

}

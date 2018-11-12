package com.algaworks.despacheweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ESTADO")
@Getter
@Setter
@EqualsAndHashCode
public class Estado implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -5726382987874948190L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="NOME")
	private String nome;
	
	@NotBlank(message="O campo Sigla é obrigatório")
	@Column(name="SIGLA")
	private String sigla;
	
	@Column(name="REGIAO")
	private Integer regiao;
	
	@PrePersist @PreUpdate
	public void prePersistAndUpdate(){
		sigla = sigla.toUpperCase();
	}

}

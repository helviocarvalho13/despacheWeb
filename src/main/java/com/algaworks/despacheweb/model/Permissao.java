package com.algaworks.despacheweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PERMISSAO")
@Getter
@Setter
@EqualsAndHashCode
public class Permissao implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -841034644167690592L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="NOME")
	private String nome;

}

package com.algaworks.despacheweb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="GRUPO")
@Getter
@Setter
@EqualsAndHashCode
public class Grupo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1786165782265932445L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="NOME")
	private String nome;
	
	@ManyToMany
	@JoinTable(name="GRUPO_PERMISSAO", joinColumns = @JoinColumn(name = "CODIGO_GRUPO"),
	inverseJoinColumns = @JoinColumn(name = "CODIGO_PERMISSAO"))
	private List<Permissao> permissoes;

}

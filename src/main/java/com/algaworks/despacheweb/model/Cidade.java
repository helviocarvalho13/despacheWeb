package com.algaworks.despacheweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CIDADE")
@Getter
@Setter
@EqualsAndHashCode
public class Cidade implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2501487278805375359L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="NOME")
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="CODIGO_ESTADO")
	@JsonIgnore
	private Estado estado;

}

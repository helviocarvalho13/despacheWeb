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
@Table(name="VEICULO")
@Getter
@Setter
@EqualsAndHashCode
public class Veiculo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 707709133255731632L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotBlank(message="O campo PLACA DO VEICULO é obrigatório")
	@Column(name="PLACA")
	private String placa;
	
	@NotBlank(message="O campo MARCA/MODELO é obrigatório")
	@Column(name="MARCA_MODELO")
	private String marcaModelo;

}

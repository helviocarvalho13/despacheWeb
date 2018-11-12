package com.algaworks.despacheweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Endereco implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1530093344224803481L;
	
	@Column(name="LOGRADOURO")
	private String logradouro;
	
	//@NotBlank(message="O campo Número é obrigatório")
	@Column(name="NUMERO")
	private String numero;
	
	//@NotBlank(message="O campo Complemento é obrigatório")
	@Column(name="COMPLEMENTO")
	private String complemento;
	
	//@NotBlank(message="O campo CEP é obrigatório")
	@Column(name="CEP")
	private String cep;
	
	@Transient
	private Estado estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_CIDADE")
	private Cidade cidade;

	public String getNomeCidadeSiglaEstado(){
		if(this.cidade != null){
			return this.cidade.getNome() + "/" + this.cidade.getEstado().getSigla();
		}
		return null;
	}
}

package com.algaworks.despacheweb.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.algaworks.despacheweb.enums.Status;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PROTOCOLO")
@Getter
@Setter
@EqualsAndHashCode
public class Protocolo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2814361976315461905L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;

	@NotBlank(message="O campo DESCRIÇÃO é obrigatório")
	@Column(name="DESCRICAO")
	private String descricao;
	
	@NotNull(message="O campo DATA é obrigatório")
	@Column(name="DATA")
	private LocalDate data;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@NotNull(message="O campo CLIENTE é obrigatório")
	@ManyToOne
	@JoinColumn(name="CODIGO_CLIENTE")
	private Cliente cliente;
	
	@Lob
	@Column(name="ASSINATURA")
	private byte[] assinatura;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private Status status = Status.CRIADO;
	
	@Transient	
	private MultipartFile file;
	
	public boolean isNovo(){
		return codigo == null;
	}
	
	public boolean isSalvarPermitido(){
		return !status.equals(Status.CANCELADO);
	}
	
	public boolean isSalvarProibido(){
		return !isSalvarPermitido();
	}
	

}

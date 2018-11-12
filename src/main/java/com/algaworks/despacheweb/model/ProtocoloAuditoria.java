package com.algaworks.despacheweb.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.algaworks.despacheweb.enums.Status;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PROTOCOLO_AUDITORIA")
@Getter
@Setter
@EqualsAndHashCode
public class ProtocoloAuditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2544478602799143241L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotNull(message="O campo DATA/HORA é obrigatório")
	@Column(name="DATA_HORA")
	private LocalDateTime dataHora;
	
	@NotNull(message="O campo PROTOCOLO é obrigatório")
	@ManyToOne
	@JoinColumn(name="CODIGO_PROTOCOLO")
	private Protocolo protocolo;
	
	@NotNull(message="O campo USUARIO é obrigatório")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CODIGO_USUARIO")
	private Usuario usuario;
	
	@NotNull(message="O campo STATUS é obrigatório")
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;

}

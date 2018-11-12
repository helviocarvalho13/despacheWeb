package com.algaworks.despacheweb.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.algaworks.despacheweb.enums.Status;
import com.algaworks.despacheweb.enums.TipoPessoa;
import com.algaworks.despacheweb.model.validation.ClienteGroupSequenceProvider;
import com.algaworks.despacheweb.model.validation.group.CnpjGroup;
import com.algaworks.despacheweb.model.validation.group.CpfGroup;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CLIENTE")
@Getter
@Setter
@EqualsAndHashCode
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -6590508034396437432L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;

	@NotBlank(message="O campo NOME é obrigatório")
	@Column(name="NOME")
	private String nome;
	
	@NotBlank(message="O campo NÚMERO DE CONTATO é obrigatório")
	@Column(name="NUMERO_CONTATO")
	private String numeroContato;
	
	@Column(name="NUMERO_CONTATO_ALTERNATIVO")
	private String numeroContatoAlternativo;
	
	@Email(message="O campo Email é inválido")
	@Column(name="EMAIL")
	private String email;
	
	@NotNull(message="O campo Tipo Pessoa é obrigatório")
	@Column(name="TIPO_PESSOA")
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;
	
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	@NotBlank(message="O campo CPF/CNPJ é obrigatório")
	@Column(name="CPF_CNPJ")
	private String cpfOuCnpj;
	
	@Column(name="RG")
	private String rg;
	
	@Column(name="DATA_EMISSAO_RG")
	private LocalDate dataEmissaoRg;
	
	@Column(name="DATA_NASCIMENTO")
	private LocalDate dataNascimento;
	
	@Column(name="ORGAO_EMISSOR_RG")
	private String orgaoEmissorRg;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private Status status = Status.CRIADO;
	
	@Transient
	private Estado estadoNaturalidade;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_NATURALIDADE")
	private Cidade naturalidade;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Embedded
	@Valid
	private Endereco endereco;
	
	@PostLoad
	private void postLoad(){
		this.cpfOuCnpj = this.tipoPessoa.formatar(this.cpfOuCnpj);
	}
	
	@PrePersist
	@PreUpdate
	private void prePersistPreUpdate(){
		this.cpfOuCnpj = TipoPessoa.removerFormatacao(cpfOuCnpj);
	}
	
	public String getCpfOuCnpjSemFormatacao(){
		return TipoPessoa.removerFormatacao(cpfOuCnpj);
	}
	
	public String getNomeNaturalidadeSiglaEstado(){
		if(this.naturalidade != null){
			return this.naturalidade.getNome() + "/" + this.naturalidade.getEstado().getSigla();
		}
		return null;
	}
	
	public boolean isNovO(){
		return codigo == null;
	}
	
	public boolean isSalvarPermitido(){
		return !status.equals(Status.CANCELADO);
	}
	
	public boolean isSalvarProibido(){
		return !isSalvarPermitido();
	}
}

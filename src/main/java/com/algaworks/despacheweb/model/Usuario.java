package com.algaworks.despacheweb.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.algaworks.despacheweb.validation.AtributoConfirmacao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USUARIO")
@Getter
@Setter
@EqualsAndHashCode
@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message="Confirmação da senha não confere")
@DynamicUpdate
public class Usuario implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -6889917253717829771L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO")
	private Long codigo;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="NOME")
	private String nome;
	
	@NotBlank(message="O campo Email é obrigatório")
	@Email(message="Email inválido")
	@Column(name="EMAIL")
	private String email;
	
	@NotBlank(message="O campo Senha é obrigatório")
	@Column(name="SENHA")
	private String senha;
	
	@Transient
	private String confirmacaoSenha;
	
	@NotNull(message="O campo Ativo é obrigatório")
	@Column(name="ATIVO")
	private Boolean ativo;
	
	//@NotNull(message="O campo Data de Nascimento é obrigatório")
	@Column(name="DATA_NASCIMENTO")
	private LocalDate dataNascimento;
	
	@NotEmpty(message="Selecione pelo menos um grupo")
	@NotNull(message="Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name="USUARIO_GRUPO", joinColumns = @JoinColumn(name = "CODIGO_USUARIO"),
				inverseJoinColumns = @JoinColumn(name = "CODIGO_GRUPO"))
	private List<Grupo> grupos;
	
	@PreUpdate
	private void preUpdate(){
		this.confirmacaoSenha = senha;
	}
	
	public boolean isNovo(){
		return codigo == null;
	}
	
	/*public String getDataIdaDefinitivaFormatada(){
		if(this.getDataNascimento() != null)
			return this.getDataNascimento().toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
		return "";
	}*/

}

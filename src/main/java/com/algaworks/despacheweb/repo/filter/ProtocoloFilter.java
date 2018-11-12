package com.algaworks.despacheweb.repo.filter;

import java.time.LocalDate;

import com.algaworks.despacheweb.model.Cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloFilter {

	private Cliente cliente;
	private LocalDate desde;
	private LocalDate ate;
	private String descricao;
}

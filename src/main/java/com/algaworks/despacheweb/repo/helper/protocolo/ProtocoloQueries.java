package com.algaworks.despacheweb.repo.helper.protocolo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.despacheweb.model.Protocolo;
import com.algaworks.despacheweb.repo.filter.ProtocoloFilter;

public interface ProtocoloQueries {
	
	public Page<Protocolo> filtrar(ProtocoloFilter protocoloFilter, Pageable pageable);

	public Protocolo retornaProtocolo(Protocolo protocolo);
}

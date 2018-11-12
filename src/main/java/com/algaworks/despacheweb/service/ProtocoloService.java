package com.algaworks.despacheweb.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.despacheweb.enums.Status;
import com.algaworks.despacheweb.model.Protocolo;
import com.algaworks.despacheweb.model.ProtocoloAuditoria;
import com.algaworks.despacheweb.repo.ProtocoloAuditoriaRepo;
import com.algaworks.despacheweb.repo.ProtocoloRepo;
import com.algaworks.despacheweb.repo.filter.ProtocoloFilter;
import com.algaworks.despacheweb.security.UsuarioSistema;

@Service
public class ProtocoloService {
	
	@Autowired
	private ProtocoloRepo protocoloRepo;
	
	@Autowired
	private ProtocoloAuditoriaRepo protocoloAuditoriaRepo;

	@Transactional
	public void salvar(Protocolo protocolo, UsuarioSistema usuarioSistema) {
		try {
			protocolo.setAssinatura(protocolo.getFile().getBytes());
			if(protocolo.isNovo()) {
				protocolo.setStatus(Status.CRIADO);
				protocoloRepo.save(protocolo);
				protocoloAuditoriaRepo.save(protocoloAuditoria(protocolo, usuarioSistema));
			}else {
				protocolo.setStatus(Status.EDITADO);
				protocoloRepo.save(protocolo);
				protocoloAuditoriaRepo.save(protocoloAuditoria(protocolo, usuarioSistema));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional
	public void cancelar(Protocolo protocolo, UsuarioSistema usuarioSistema) {
		protocolo.setStatus(Status.CANCELADO);
		protocoloRepo.save(protocolo);
		protocoloAuditoriaRepo.save(protocoloAuditoria(protocolo, usuarioSistema));
	}
	
	public ProtocoloAuditoria protocoloAuditoria(Protocolo protocolo, UsuarioSistema usuarioSistema) {
		ProtocoloAuditoria pa = new ProtocoloAuditoria();
		pa.setDataHora(LocalDateTime.now());
		pa.setProtocolo(protocolo);
		pa.setStatus(protocolo.getStatus());
		pa.setUsuario(usuarioSistema.getUsuario());
		return pa;
	}
	
	public Page<Protocolo> filtrar(ProtocoloFilter protocoloFilter, Pageable pageable) {
		return protocoloRepo.filtrar(protocoloFilter, pageable);
	}

	public Protocolo retornaProtocolo(Protocolo protocolo) {
		return protocoloRepo.retornaProtocolo(protocolo);
	}


}

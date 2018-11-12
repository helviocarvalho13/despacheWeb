package com.algaworks.despacheweb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.ProtocoloAuditoria;
import com.algaworks.despacheweb.repo.helper.protocoloAuditoria.ProtocoloAuditoriaQueries;

@Repository
public interface ProtocoloAuditoriaRepo extends JpaRepository<ProtocoloAuditoria, Long>, ProtocoloAuditoriaQueries{

}

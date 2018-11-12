package com.algaworks.despacheweb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.Protocolo;
import com.algaworks.despacheweb.repo.helper.protocolo.ProtocoloQueries;

@Repository
public interface ProtocoloRepo extends JpaRepository<Protocolo, Long>, ProtocoloQueries{

}

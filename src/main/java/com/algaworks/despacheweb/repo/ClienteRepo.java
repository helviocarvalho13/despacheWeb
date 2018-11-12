package com.algaworks.despacheweb.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.Cliente;
import com.algaworks.despacheweb.repo.helper.cliente.ClienteQueries;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Long>, ClienteQueries{

	Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpjSemFormatacao);

	List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}

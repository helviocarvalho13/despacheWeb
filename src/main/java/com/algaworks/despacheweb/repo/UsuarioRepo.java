package com.algaworks.despacheweb.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.despacheweb.model.Usuario;
import com.algaworks.despacheweb.repo.helper.usuario.UsuarioQueries;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long>, UsuarioQueries{

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByCodigoIn(Long[] codigos);


}

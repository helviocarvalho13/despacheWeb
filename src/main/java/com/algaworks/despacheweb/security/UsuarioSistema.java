package com.algaworks.despacheweb.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.algaworks.despacheweb.model.Usuario;

import lombok.Getter;

@Getter
public class UsuarioSistema extends User{
	
	private Usuario usuario;
	
	private static final long serialVersionUID = -2454321866669036398L;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	


}

package com.algaworks.despacheweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.algaworks.despacheweb.security.AppUserDetailService;

/**
 * @author Helvio Carvalho
 */

/*
 * Não foi colocado a anotação @configuration, pois a anotação @EnableWebSecurity já possui na sua codificação.
 * Se não tivesse a anotação de segurança, é obrigatório a inclusão da anotação @configuration, uma vez que se trata
 * de uma classe de configuração do framework. 
 */
@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web){
		web.ignoring()
		.antMatchers("/layout/**")
		.antMatchers("/images/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			//hasRole e hasAuthority: O padrão da string do hasRole é "ROLE_ + ''", já o hasAuthority não possui.
				.antMatchers("/perfis/**").hasRole("MODULO_ADMINISTRACAO_PERFIL_ACESSO")
				.antMatchers("/usuarios/**").hasRole("MODULO_ADMINISTRACAO_USUARIO")
				.antMatchers("/clientes/**").hasRole("MODULO_CADASTRO_CLIENTE")
				.antMatchers("/protocolos/**").hasRole("MODULO_PROTOCOLO_PROTOCOLO")
				.anyRequest().authenticated()
				//O 'anyRequest().denyAll()' nega acesso a todas as paginas, e permite acesso, apenas se tiver a ROLE especifica.
				//.anyRequest().denyAll()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()	
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.sessionManagement()
				.invalidSessionUrl("/login");
		/*
				.and()
			.sessionManagement()
				.maximumSessions(1)
				.expiredUrl("/login");*/
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}

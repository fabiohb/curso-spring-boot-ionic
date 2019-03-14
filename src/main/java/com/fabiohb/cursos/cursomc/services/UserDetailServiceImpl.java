package com.fabiohb.cursos.cursomc.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.repositories.ClienteRepository;
import com.fabiohb.cursos.cursomc.security.UserSS;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(
			cliente.getId(),
			cliente.getEmail(),
			cliente.getSenha(),
			cliente.getPerfis().stream()
				.map(p -> new SimpleGrantedAuthority(p.getDescricao()))
				.collect(Collectors.toList())
		);
	}

}

package com.fabiohb.cursos.cursomc.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = -5004002392792564165L;

	private Integer id;

	private String email;

	private String senha;

	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
		super();
	}

	public UserSS(Integer id, String email, String senha, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = authorities;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

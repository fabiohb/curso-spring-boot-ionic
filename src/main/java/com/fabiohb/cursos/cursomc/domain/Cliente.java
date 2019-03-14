package com.fabiohb.cursos.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fabiohb.cursos.cursomc.domain.enums.Perfil;
import com.fabiohb.cursos.cursomc.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private String nome;

	@Column(unique = true)
	private String email;

	private String cpfOuCnpj;

	private Integer tipo;
	
	@JsonIgnore
	private String senha;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIL")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Set<Integer> perfis = new HashSet<>();

	@OneToMany(mappedBy = "cliente")
	@JsonIgnore
	private List<Pedido> pedidos = new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		this();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		this.senha = senha;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream()
			.map(Perfil::toEnum)
			.collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
}

package com.fabiohb.cursos.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "estado_id")
	@JsonManagedReference
	private Estado estado;

	public Cidade() {
		super();
	}

	public Cidade(Integer id, String nome, Estado estado) {
		this();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}

}

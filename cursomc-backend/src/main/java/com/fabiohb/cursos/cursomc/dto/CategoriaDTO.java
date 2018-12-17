package com.fabiohb.cursos.cursomc.dto;

import java.io.Serializable;

import com.fabiohb.cursos.cursomc.domain.Categoria;

import lombok.Data;

@Data
public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;
	
	public CategoriaDTO() {
		super();
	}
	
	public CategoriaDTO(Categoria categoria) {
		this();
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
	
}

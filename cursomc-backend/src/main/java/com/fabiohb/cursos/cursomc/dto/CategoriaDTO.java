package com.fabiohb.cursos.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fabiohb.cursos.cursomc.domain.Categoria;

import lombok.Data;

@Data
public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty
	@Size(min = 5, max = 80, message = "O tamanho de ser entre 5 e 80 caracteres")
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
